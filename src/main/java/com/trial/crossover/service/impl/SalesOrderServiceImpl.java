package com.trial.crossover.service.impl;

import com.trial.crossover.dao.SalesOrderDAO;
import com.trial.crossover.dto.SalesOrderDTO;
import com.trial.crossover.model.SalesOrder;
import com.trial.crossover.service.SalesOrderService;
import com.trial.crossover.transformer.GenericTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
	private GenericTransformer<SalesOrderDTO, SalesOrder> transformer;

	@Override
	@Transactional(readOnly = true)
	public List<SalesOrderDTO> all() {
		List<SalesOrder> orders = salesOrderDAO.all();
		List<SalesOrderDTO> dtos = new ArrayList<>(orders.size());

		for (SalesOrder s : orders) {
			dtos.add(transformer.getDTOFromModel(s, SalesOrderDTO.class));
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

		return transformer.getDTOFromModel(s, SalesOrderDTO.class);
	}

	@Override
	@Transactional
	public SalesOrderDTO create(SalesOrderDTO dto) {
		return null;
	}

	@Override
	@Transactional
	public SalesOrderDTO update(SalesOrderDTO dto) {
		return null;
	}

	@Override
	@Transactional
	public void delete(long id) {
		salesOrderDAO.delete(id);
	}
}
