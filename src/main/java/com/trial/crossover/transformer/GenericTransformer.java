package com.trial.crossover.transformer;

import com.trial.crossover.dto.DTO;
import com.trial.crossover.dto.Entity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Component
public class GenericTransformer<T extends DTO, K extends Entity> {

	public K getModelFromDTO(T dto, Class<K> clazz) {
		K obj = null;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(dto, obj, getNullPropertyNames(dto));
		return obj;
	}

	public T getDTOFromModel(K obj, Class<T> clazz) {
		T dto = null;
		try {
			dto = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(obj, dto, getNullPropertyNames(obj));
		return dto;
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

}
