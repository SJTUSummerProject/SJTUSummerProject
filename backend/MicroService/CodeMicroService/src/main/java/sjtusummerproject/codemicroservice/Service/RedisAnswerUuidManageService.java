package sjtusummerproject.codemicroservice.Service;

public interface RedisAnswerUuidManageService {
    public String QueryAnswerRedis(String Uuid);
    public String AddAnswerUuidRedis(String Uuid,String Answer);
}
