# The API of The Backend Service

#### 注：所有请求参数均应使用UTF-8然后URL Encoding方式编码。使用POST方式调用时，应制定Content-Type为"application/x-www-form-urlencoded"
* ## SignMicroservice
    * ### URL：POST /Sign/Up
        #### 传入参数
        变量名| 类型 | 说明 
         -| - | -
         answer       | String         | 验证码答案
         username     | String         | 用户名
         password     | String         | 密码
         email        | String         | 邮箱
        #### 返回参数：String
        字符串|说明
        -| -
         code    | 验证码错误
         success | 成功注册，等待邮件确认
         resend  | 用户名已经被注册但是还未激活，后端会重新发送邮件
         exited  | 用户名已经存在且已经被激活
    * ### URL：POST /Sign/In
        #### 传入参数
        变量名|类型|说明
        -|-|-
        answer    | String     | 验证码答案
        username  | String     | 用户名
        password  | String     | 密码
        #### 返回参数：String
        字符串|说明
        -| -
        code     | 验证码错误
        fail     | 用户名或密码错误
        UnActive | 用户未激活，无需重新发送邮件
        UUID     | 登陆成功，前端将该字符串存入cookie中,持久化
    * ### URL：POST /Sign/Out
        #### 传入参数
        变量名|类型|说明
        -|-|-
        token     | String    |cookie中持久化储存的token
        #### 返回参数：void

        
* ## TicketMicroservice
    ### 票品信息
        变量名              说明
        id              票品唯一标识符
        type            票品类型
        dates           所有日期，用逗号分隔
        startDate       起始日期
        endDate         终止日期
        time            时间
        city            城市
        venue           地址
        title           标题
        image           图片
        intro           简介
        stock           库存
        lowerprice      底价
        highprice       顶价 
    ### 票品类型
        变量名             说明
        parenting       儿童亲子
        pera            歌剧话剧
        acrobatics      曲艺杂技
        sports          体育赛事
        dancing         舞蹈芭蕾
        concert         音乐会
        vocal concert   演唱会
    * ### URL：GET /Ticket/QueryShowPage
        #### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber   | String | 每页返回3*6=18条票品
        #### 返回参数：json
        变量名|说明
        -|-|-
        content   | 票品信息
        totalElements | 总票品数量
        totalPages    | 总页数
        number        | 当前页码（0为第一页）
    * ### URL：GET /Ticket/QueryById
        ### 传入参数
        变量名|类型|说明
        -|-|-
        id           |Long    | 标识票品的Id
        ### 返回参数：json
    * ### URL：GET /Ticket/QueryByTypePage
        ### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        type       | String | 票品类型
    * ### URL：GEt /Ticket/QueryByCityPage
        ### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        city       | String | 所在城市
    * ### URL：GET /Ticket/QueryByDateRangePage
        ### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        firstdate  | STring | 起始日期
        seconddate | STring | 截止日期
    * ### URL：GET /Ticket/QueryByPriceRangePage
        ### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        lowprice   | String | 最低价格
        highprice  | String | 最高价格

    * ### URL： GET /Ticket/QueryByCityAndDateRangePage
    * ### URL： GET /Ticket/QueryByCityAndPriceRangePage
    * ### URL： GET /Ticket/QueryByCityAndPriceRangeAndDateRangePage



* ## EmailMicrosevice
    * ### URL：GET /Email/Active
        #### 传入参数
        变量名|类型|说明
        -|-|-
        code     | String | 其实就是UUID
        #### 返回参数：boolean

* ## CodeMicroservice
    * ### URL：GET /Code/Generate
        #### 传入参数：void
        #### 返回参数：void

