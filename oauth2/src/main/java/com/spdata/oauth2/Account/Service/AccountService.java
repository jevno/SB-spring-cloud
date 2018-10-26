package com.spdata.oauth2.Account.Service;

import com.spdata.entity.Base.BaseService;
import com.spdata.entity.Role.Role;
import com.spdata.oauth2.Account.Dao.AccountDao;
import com.spdata.oauth2.Account.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService extends BaseService<AccountDao, Account> implements UserDetailsService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountDao.findByAccount(s);
        if (account == null) {
            throw new UsernameNotFoundException("用户名不存在或密码错误!");
        }
        return account;
    }

    public List<Role> findRole(Integer userid) {
        return accountDao.findRole(userid);
    }
}
