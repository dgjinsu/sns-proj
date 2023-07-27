package com.snsproj.repository;

import com.snsproj.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserCacheRepository {
    private final RedisTemplate<String, User> userRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3); // 3일의 만료기간 세팅

    public void setUser(User user) {
        log.info("Set User to Redis {}:{}", getKey(user.getUsername()), user, USER_CACHE_TTL);
        userRedisTemplate.opsForValue().set(getKey(user.getUsername()), user, USER_CACHE_TTL);
    }

    public Optional<User> getUser(String userName) {
        User user = userRedisTemplate.opsForValue().get(getKey(userName));
        log.info("Get User to Redis {}:{}", getKey(userName), user);
        return Optional.ofNullable(user);
    }

    //key 값에 프리픽스를 붙여줌
    private String getKey(String userName) {
        return "USER:" + userName;
    }
}
