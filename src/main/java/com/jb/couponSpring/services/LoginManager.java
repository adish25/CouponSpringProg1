package com.jb.couponSpring.services;

import com.jb.couponSpring.beans.ClientType;
import com.jb.couponSpring.exceptions.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {

    @Autowired
    private ApplicationContext ctx;

    public ClientService login(String email, String password, ClientType clientType) throws CouponSystemException {

        ClientService clientService = null;

        switch (clientType) {

            case ADMINISTRATOR:
                clientService = (AdminServiceImpl) ctx.getBean(AdminServiceImpl.class);
                clientService.login(email, password);
                break;

            case COMPANY:
                clientService = (CompanyServiceImpl) ctx.getBean(CompanyServiceImpl.class);
                clientService.login(email, password);
                break;

            case CUSTOMER:
                clientService = (CustomerServiceImpl) ctx.getBean(CustomerServiceImpl.class);
                clientService.login(email, password);
                break;

            default:
                break;
        }

        return clientService;
    }
}
