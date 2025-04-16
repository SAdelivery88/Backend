package group1.sa_delivery.Security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group1.sa_delivery.dao.UserMapper;
import group1.sa_delivery.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userDetailService {

    @Autowired
    private UserMapper usermapper;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("No authenticated user found");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            User user = usermapper.selectOne(wrapper);
            if(user==null)
                throw new UsernameNotFoundException("User not found with username: " + username);
            return user;
        } else if (principal instanceof String) {
            String username = (String) principal;
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            User user = usermapper.selectOne(wrapper);
            if(user==null)
                throw new UsernameNotFoundException("User not found with username: " + username);
            return user;
        }

        throw new UsernameNotFoundException("User details not found in SecurityContext");
    }
}
