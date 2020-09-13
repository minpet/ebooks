package com.minpet.data;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.minpet.local.interf.IImageRepository;
import com.minpet.model.EbookImage;

@ApplicationScoped
public class ImageEbookRepository implements IImageRepository{

	@PersistenceContext
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EbookImage saveEbookImage(long ebookId, String mimeType, String encodeFileToBase64Binary) {
		EbookImage im = new EbookImage();
		im.setContent(encodeFileToBase64Binary);
		im.setContentType(mimeType);
		im.setEbookId(ebookId);
		
		em.persist(im);
		return im;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EbookImage findImageForEbook(Long id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EbookImage> criteria = cb.createQuery(EbookImage.class);
        Root<EbookImage> image = criteria.from(EbookImage.class);
        criteria.select(image)
        	.where(cb.equal(image.get("ebookId"), id));
        try {
        	return em.createQuery(criteria).getSingleResult();
        } catch(NoResultException e) {
        	return null;
        }
	}
	
}
