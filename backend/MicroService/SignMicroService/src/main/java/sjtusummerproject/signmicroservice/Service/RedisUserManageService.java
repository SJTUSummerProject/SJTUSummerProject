package sjtusummerproject.signmicroservice.Service;

public interface RedisUserManageService {
    public String QueryUserStatusRedis(String username);
    public String AddUserStatusRedis(String username);
    public void AddTokenAuthRedis(String token, String auth);
    public void DeleteTokenRedis(String token);
}
