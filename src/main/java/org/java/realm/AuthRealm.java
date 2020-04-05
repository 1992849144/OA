package org.java.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.java.entity.SysUser;
import org.java.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 授权的方法
     * @param principals
     * @return
     * 当前方法，只要在进行权限判断时，才会执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

       System.out.println("-------------准备到数据库得到用户的访问权限--------");

        //获得用户的凭证信息
        Map map = (Map) principals.getPrimaryPrincipal();

        //从map中，获得userId
        Integer userId= (Integer) map.get("id");

        //根据用户信息到数据库中加载用户权限(模拟读数据库的权限)
        List<String> list = sysUserService.loadPermission(userId);

        //封装成AuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(list);

        return info;
    }

    /**
     * 认证的方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获得用户信息
        String principal = (String) token.getPrincipal();
        //根据用户信息（用户名）到数据库中判断，用户是否存在

        Map user = sysUserService.login(principal);

        if (user==null){
            return null;
        }

        //用户名存在，返回该用户的正确密码   //123,accp,3
        String pwd = user.get("password").toString();

        //设置盐
        String salt="accp";

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,pwd, ByteSource.Util.bytes(salt),"myRealm");

        return info;
    }
}
