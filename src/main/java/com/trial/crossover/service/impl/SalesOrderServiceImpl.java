package com.trial.crossover.service.impl;

import com.trial.crossover.dao.CustomerDAO;
import com.trial.crossover.dao.ProductDAO;
import com.trial.crossover.dao.SalesOrderDAO;
import com.trial.crossover.dto.CustomerDTO;
import com.trial.crossover.dto.FieldErrorDTO;
import com.trial.crossover.dto.ProductDTO;
import com.trial.crossover.dto.SalesOrderDTO;
import com.trial.crossover.dto.SalesOrderEditionDTO;
import com.trial.crossover.dto.SalesOrderProductDTO;
import com.trial.crossover.model.Customer;
import com.trial.crossover.model.Product;
import com.trial.crossover.model.SalesOrder;
import com.trial.crossover.model.SalesOrderProduct;
import com.trial.crossover.service.SalesOrderService;
import com.trial.crossover.transformer.GenericTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by: dambros
 * Date: 12/3/2015
 */
@Service
public class SalesOrderServiceImpl implements SalesOrderService {

	@Autowired
	private SalesOrderDAO salesOrderDAO;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private GenericTransformer<CustomerDTO, Customer> customerTransformer;

	@Autowired
	private GenericTransformer<ProductDTO, Product> productTransformer;

	@Override
	@Transactional(readOnly = true)
	public List<SalesOrderDTO> all() {
		List<SalesOrder> orders = salesOrderDAO.all();
		List<SalesOrderDTO> dtos = new ArrayList<>(orders.size());

		for (SalesOrder s : orders) {
			dtos.add(getOrderDTO(s));
		}

		return dtos;
	}

	@Override
	@Transactional(readOnly = true)
	public SalesOrderDTO get(long id) {
		SalesOrder s = salesOrderDAO.get(id);

		if (s == null) {
			return null;
		}

		return getOrderDTO(s);
	}

	@Override
	@Transactional
	public Object create(SalesOrderEditionDTO dto) {
		Object obj = validateBusinessLogic(dto);

		if (obj instanceof SalesOrder) {
			SalesOrder order = salesOrderDAO.create((SalesOrder) obj);

			//update products quantities
			for (SalesOrderProduct s : order.getOrderProducts()) {
				s.getProduct().setAvailableQuantity(s.getProduct().getAvailableQuantity() - s.getProductQuantity());
			}

			return getOrderDTO(order);
		}

		//return errors list
		return obj;
	}

	@Override
	@Transactional
	public Object update(SalesOrderEditionDTO dto) {
		SalesOrder order = salesOrderDAO.get(dto.getId());

		if (order == null) {
			return new ArrayList<>(Arrays.asList(new FieldErrorDTO("id", "invalid value")));
		}

		List<SalesOrderProduct> previousProducts = order.getOrderProducts();
		float previousAmount = order.getTotalPrice();

		Object obj = validateBusinessLogic(dto);

		if (obj instanceof SalesOrder) {

			for (SalesOrderProduct prod : previousProducts) {
				//return products to stock
				prod.getProduct().setAvailableQuantity(prod.getProduct().getAvailableQuantity() + prod.getProductQuantity());
			}

			//return credit to customer
			Customer c = order.getCustomer();
			c.setCreditLimit(c.getCreditLimit() + previousAmount);
			c.setCurrentCredit(c.getCurrentCredit() - previousAmount);

			order = salesOrderDAO.update((SalesOrder) obj);

//			update new products quantities
			for (SalesOrderProduct s : order.getOrderProducts()) {
				s.getProduct().setAvailableQuantity(s.getProduct().getAvailableQuantity() - s.getProductQuantity());
			}

			//update new customer credit
			c.setCurrentCredit(c.getCurrentCredit() + order.getTotalPrice());
			c.setCreditLimit(c.getCreditLimit() - order.getTotalPrice());
			customerDAO.update(c);

			return getOrderDTO(order);
		}

		//return errors list
		return obj;

	}

	@Override
	@Transactional
	public void delete(long id) {
		salesOrderDAO.delete(id);
	}

	private Object validateBusinessLogic(SalesOrderEditionDTO dto) {
		//checking if products and customer have valid ids
		Customer customer = customerDAO.get(dto.getCustomer());
		List<FieldErrorDTO> errors = new ArrayList<>();

		if (customer == null) {
			errors.add(new FieldErrorDTO("customer", "invalid value"));
		}

		List<SalesOrderProduct> orderProducts = new ArrayList<>(dto.getOrderProducts().size());

		for (int i = 0; i < dto.getOrderProducts().size(); i++) {
			Product p = productDAO.get(dto.getOrderProducts().get(i).getProduct());
			if (p == null) {
				errors.add(new FieldErrorDTO("product[" + i + "]", "invalid value"));
			} else {
				SalesOrderProduct salesProd = new SalesOrderProduct();
				salesProd.setProduct(p);
				salesProd.setProductQuantity(dto.getOrderProducts().get(i).getProductQuantity());
				orderProducts.add(salesProd);
			}
		}

		//return with validation errors, if present
		if (errors.size() > 0) {
			return errors;
		}

		float totalPrice = 0;
		//all products in the list have been validated and are valid
		for (SalesOrderProduct s : orderProducts) {
			//check if requested quantity is available
			if (s.getProductQuantity() > s.getProduct().getAvailableQuantity()) {
				errors.add(new FieldErrorDTO(s.getProduct().getDescription(), "invalid quantity"));
			}
			totalPrice += (s.getProductQuantity() * s.getProduct().getPrice());
		}

		if (dto.getTotalPrice() > (customer.getCreditLimit() - customer.getCurrentCredit())) {
			errors.add(new FieldErrorDTO("customer", "not enough credit"));
		}

		if (dto.getTotalPrice() != totalPrice) {
			errors.add(new FieldErrorDTO("totalPrice", "incorrect amount"));
		}

		//return with quantity or credit errors, if present
		if (errors.size() > 0) {
			return errors;
		}

		customer.setCurrentCredit(customer.getCurrentCredit() + dto.getTotalPrice());
		customer.setCreditLimit(customer.getCreditLimit() - dto.getTotalPrice());

		SalesOrder order = new SalesOrder();
		order.setId(dto.getId());
		order.setCustomer(customer);
		order.setOrderProducts(orderProducts);
		order.setTotalPrice(dto.getTotalPrice());
		order.setOrderNumber(dto.getOrderNumber());

		return order;
	}

	private SalesOrderDTO getOrderDTO(SalesOrder order) {
		SalesOrderDTO orderDTO = new SalesOrderDTO();
		orderDTO.setId(order.getId());
		List<SalesOrderProductDTO> prods = new ArrayList<>(order.getOrderProducts().size());
		for (SalesOrderProduct p : order.getOrderProducts()) {
			SalesOrderProductDTO salesDto = new SalesOrderProductDTO();
			salesDto.setProduct(productTransformer.getDTOFromModel(p.getProduct(), ProductDTO.class));
			salesDto.setProductQuantity(p.getProductQuantity());
			salesDto.setId(p.getId());
			prods.add(salesDto);
		}
		orderDTO.setOrderProducts(prods);
		orderDTO.setTotalPrice(order.getTotalPrice());
		orderDTO.setOrderNumber(order.getOrderNumber());
		orderDTO.setCustomer(customerTransformer.getDTOFromModel(order.getCustomer(), CustomerDTO.class));

		return orderDTO;
	}
}
