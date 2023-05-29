package com.yozora.anime.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yozora.anime.auth.JwtTokenProvider;
import com.yozora.anime.entity.NovelUsersEntity;
import com.yozora.anime.service.NovelUsersService;
import com.yozora.anime.utils.R;
import com.yozora.anime.utils.RET;
import com.yozora.anime.vo.QueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;



/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-04-29 11:00:05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Api(value = "novelUsers",tags = "")
public class NovelUsersController {

    private final NovelUsersService novelUsersService;

    private final AuthenticationManager authenticationManager;


    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate redisTemplate;

    // 错误次数key统计前缀
    private static final String LOGIN_ERROR_COUNT_PREFIX = "LOGIN_ERROR_COUNT_";

    // 上次验证码时间key统计前缀
    private static final String VERIFY_CODE_LAST_TIME_PREFIX = "VERIFY_CODE_LAST_TIME_";


    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    /**
     * 列表
     */

    @ApiOperation(value="分页查询",notes="分页查询")
    @PostMapping("/page")
    public R list(@RequestBody QueryVo<NovelUsersEntity> entityQueryVo){
        return  R.ok(novelUsersService.page(entityQueryVo.getPage(),Wrappers.query(entityQueryVo.getEntity())));

    }

    @ApiOperation(value = "获取当前用户详情", notes="获取当前用户详情")
    @GetMapping("/getUserDetail")
    public R getUserDetail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            NovelUsersEntity usersEntity = novelUsersService.getOne(Wrappers.<NovelUsersEntity>query().eq("username", currentUserName));
            usersEntity.setPassword("");
            if(null!=usersEntity){
                return R.ok(usersEntity);
            }
        }
         return R.failed(RET.UNAUTHORIZED);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		NovelUsersEntity novelUsers = novelUsersService.getById(id);
        return R.ok(novelUsers);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @RequestMapping("/save")
    public R save(@RequestBody NovelUsersEntity novelUsers){
		novelUsersService.save(novelUsers);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public R update(@RequestBody NovelUsersEntity novelUsers){
        NovelUsersEntity usersEntity = novelUsersService.getById(novelUsers);
        usersEntity.setNickname(novelUsers.getNickname());
        usersEntity.setSign(novelUsers.getSign());
        usersEntity.setEmail(novelUsers.getEmail());
        novelUsersService.updateById(usersEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		novelUsersService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping("/login")
    @ResponseBody
    public R login(@RequestBody NovelUsersEntity loginDto) {


        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            String token = jwtTokenProvider.generateToken(loginDto.getUsername());
            clearLoginErrorCount(loginDto.getUsername());
            updateLastVerifyCodeTime(loginDto.getUsername());
            return R.ok(token);
        } catch (AuthenticationException ex) {
            setLoginErrorCount(loginDto.getUsername() );
            return R.failed(RET.UNAUTHORIZED);
        }
    }

    private void setLoginErrorCount(String username) {
        String key = generateKey(LOGIN_ERROR_COUNT_PREFIX, username);
        redisTemplate.opsForValue().increment(key, 1);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
    }

    private void clearLoginErrorCount(String username) {
        String key = generateKey(LOGIN_ERROR_COUNT_PREFIX, username);
        redisTemplate.delete(key);
    }


    private void updateLastVerifyCodeTime(String username) {
        String key = generateKey(VERIFY_CODE_LAST_TIME_PREFIX, username);
        redisTemplate.opsForValue().set(key, System.currentTimeMillis());
    }

    private String generateKey(String prefix, String username) {
        return prefix + username;
    }
}
