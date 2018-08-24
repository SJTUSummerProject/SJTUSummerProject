package sjtusummerproject.codemicroservice.Service;

public interface RedisAnswerUuidManageService {
    public String QueryAnswerRedis(String Uuid);
    public void AddAnswerUuidRedis(String Uuid,String Answer);
}
