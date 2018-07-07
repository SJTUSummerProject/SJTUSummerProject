package sjtusummerproject.signmicroservice.Service;

public interface RedisUserManageService {
    public String QueryUserStatusRedis(String username);
    public String AddUserStatusRedis(String username);
    public String QueryUserPasswordRedis(String username);
    public String AddUserPasswordRedis(String username, String password);
}
