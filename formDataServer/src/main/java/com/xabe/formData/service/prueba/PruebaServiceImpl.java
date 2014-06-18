package com.xabe.formData.service.prueba;

				
				
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xabe.formData.model.prueba.Prueba;
import com.xabe.formData.persistence.prueba.PruebaMapper;
import com.xabe.formData.persistence.PaginationContext;
import com.xabe.formData.model.prueba.PruebaExample;

 
@Service("pruebaService")
@Transactional(readOnly = true, isolation=Isolation.READ_COMMITTED)
public class PruebaServiceImpl implements PruebaService {
	@Autowired
	private PruebaMapper pruebaMapper;
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void add(Prueba aPrueba) {
		pruebaMapper.insert(aPrueba);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void update(Prueba aPrueba) {
					pruebaMapper.updateByPrimaryKey(aPrueba);
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void update(Prueba aPrueba, PruebaExample aPruebaExample) {
					pruebaMapper.updateByExample(aPrueba, aPruebaExample);
			}

		@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void delete(Prueba aPrueba) {
					PruebaExample aPruebaExample = new PruebaExample();
			aPruebaExample.createCriteria().andIdEqualTo(aPrueba.getId());
			pruebaMapper.deleteByExample(aPruebaExample);
			}
		
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void delete(PruebaExample aPruebaExample) {
					pruebaMapper.deleteByExample(aPruebaExample);
			}

	public List<Prueba> getAll() {		
		return pruebaMapper.selectByExample(new PruebaExample());
	}

	public List<Prueba> getAll(PruebaExample aPruebaExample) {		
		return pruebaMapper.selectByExample(aPruebaExample);
	}
	
	public List<Prueba> getPaginated(PruebaExample example,PaginationContext paginationContext) {
		int page = paginationContext.getCurrentPage();
		if(page < 1)
		{
			page = 1;
		}
		paginationContext.setSkipResults(paginationContext.getMaxResults() * (page - 1));
		paginationContext.updateTotalCount(pruebaMapper.countByExample(example));
		example.setLimit(paginationContext.getSkipResults() + paginationContext.getMaxResults());
		example.setOffset(paginationContext.getSkipResults());
		return  pruebaMapper.selectByExamplePagination(example);
	}
}
	