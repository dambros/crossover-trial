package com.trial.crossover.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Component
public class GenericTransformer<T, K> {

	public K getModelFromDTO(T dto, Class<K> clazz) {
		K obj = null;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(dto, obj);
		return obj;
	}

	public T getDTOFromModel(K obj, Class<T> clazz) {
		T dto = null;
		try {
			dto = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(obj, dto);
		return dto;
	}

}
