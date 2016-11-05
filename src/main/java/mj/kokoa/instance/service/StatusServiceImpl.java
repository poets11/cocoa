package mj.kokoa.instance.service;

import mj.kokoa.instance.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

/**
 * Created by poets11 on 2016. 11. 3..
 */
@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StatusRepository statusRepository;

}
