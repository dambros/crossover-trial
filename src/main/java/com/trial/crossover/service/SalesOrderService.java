package com.trial.crossover.service;

import com.trial.crossover.dto.SalesOrderEditionDTO;
import com.trial.crossover.dto.SalesOrderDTO;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public interface SalesOrderService {
	List<SalesOrderDTO> all();
	SalesOrderDTO get(long id);
	Object create(SalesOrderEditionDTO dto);
	Object update(SalesOrderEditionDTO dto);
	void delete(long id);
}
