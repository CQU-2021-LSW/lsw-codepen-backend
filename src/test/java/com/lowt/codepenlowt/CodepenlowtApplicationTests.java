package com.lowt.codepenlowt;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CodepenlowtApplicationTests {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    RedisTemplate redisTemplate;
//
//    @Test
//    void contextLoads() {
//        // Success
//        SimpleMailMessage message = new SimpleMailMessage();
//        // 发件人
//        message.setFrom("544851589@qq.com");
//        // 收件人
//        message.setTo("lowtaste@foxmail.com");
//        // 邮件标题
//        message.setSubject("Java发送邮件测试");
//        // 邮件内容
//        message.setText("你好，这是一条用于测试Spring Boot邮件发送功能的邮件！哈哈哈~~~");
//        // 抄送人
//        message.setCc("544851589@qq.com");
//        mailSender.send(message);
//    }
//
//    @Test
//    public void getLikedCountFromRedis() {
//        Cursor cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
//        List<LikedCountDTO> list = new ArrayList<>();
//        while (cursor.hasNext()) {
//            Map.Entry<Object, Object> map = (Map.Entry<Object, Object>) cursor.next();
//            //将点赞情况存储在 LikedCountDTO
//            String key = (String) map.getKey();
//            LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
//            list.add(dto);
//        }
//        System.out.println(list);
//    }
//
//    @Test
//    void test() {
//        System.out.println(redisTemplate.opsForHash().get("DAMN", "LL"));
//    }
}
