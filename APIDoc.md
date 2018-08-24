# The API of The Backend Service

#### 注：所有请求参数均应使用UTF-8然后URL Encoding方式编码。使用POST方式调用时，应制定Content-Type为"application/x-www-form-urlencoded"
* ## SignMicroservice port:30004
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

        
* ## TicketMicroservice port:30005
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
        #### 传入参数
        变量名|类型|说明
        -|-|-
        id           |Long    | 标识票品的Id
        ### 返回参数：json
    * ### URL：GET /Ticket/QueryByTypePage
        #### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        type       | String | 票品类型
    * ### URL：GEt /Ticket/QueryByCityPage
        #### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        city       | String | 所在城市
    * ### URL：GEt /Ticket/QueryByCityAndTypePage
        说明：若city或type为all，则自动调用对应的api
        #### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        city       | String | 所在城市
        type       | String | 类型
        title      | String | 标题
    * ### URL：GET /Ticket/QueryByDateRangePage
        #### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        firstdate  | STring | 起始日期
        seconddate | STring | 截止日期
    * ### URL：GET /Ticket/QueryByPriceRangePage
        #### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        lowprice   | String | 最低价格
        highprice  | String | 最高价格
    * ### URL：GET /Ticket/QueryByTitle
        #### 传入参数
        变量名|类型|说明
        -|-|-
        pagenumber | String |
        title       | String | 
    * ### URL： GET /Ticket/QueryByCityAndDateRangePage
    * ### URL： GET /Ticket/QueryByCityAndPriceRangePage
    * ### URL： GET /Ticket/QueryByCityAndPriceRangeAndDateRangePage



* ## EmailMicrosevice port:30003
    * ### URL：GET /Email/Active
        #### 传入参数
        变量名|类型|说明
        -|-|-
        code     | String | 其实就是UUID
        #### 返回参数：boolean

* ## CodeMicroservice port:30001
    * ### URL：GET /Code/Generate
        #### 传入参数：void
        #### 返回参数：void

* ## CartMicroservice port:30007
    ### 购物车信息
        变量名          说明
        id              购物车物品唯一标识符（一堆购物车物品组成了购物车）
        userId          购物车物品拥有者id
        ticketId        对应想要购买的票品id
        price           价格
        number          数量
        image           图片的url
        title           主题
        date            顾客选定的日期
        venue           地址
        city            城市
    ### response头中errorNum说明
        数字            说明
        0               成功
        1               尚未登录
        2               身份不对应
        3               账户被冻结
    * ### URL：/Cart/SaveInDetailPage
        #### 传入参数
        变量名|类型|说明
        -|-|-       
        token | String | 用户标识
        ticketid | String | 票品标识
        price | Double | 价格
        date | String | 日期
        number | Long | 数量
        #### 返回参数（String）
    * ### URL：/Cart/NumberEditInCart
        #### 传入参数
        变量名|类型|说明
        -|-|-  
        token | String |
        entryid | Long | 购物车物品唯一标识
        number | Long |
        #### 返回参数（String）
    * ### URL：/Cart/DeleteBatchInCart
        #### 传入参数
        变量名|类型|说明
        -|-|-  
        token | String |
        btachentryid | Array | 要批量删除的购物车物品的id数组
        #### 返回参数（String）
    * ### URL：/Cart/QueryByUserId
        #### 传入参数
        变量名|类型|说明
        -|-|-  
        pagenumber | int | 查询页数
        token | String
        #### 返回参数（分页了的cart信息，默认一页16个）

* ## UserDetailMicroservice port：30009
    ### response头中errorNum说明
        数字            说明
        0               成功
        1               尚未登录
        2               身份不对应
        3               账户被冻结
    ### 用户详细信息
        变量名          说明
        id              用户唯一标识
        username        用户名
        email           邮箱
        avatar          头像
        phone           手机号（用于收货）
        address         地址（用于收货）
        nickName        昵称
        account         账户余额
    * ### URL：/UserDetail/InitialSave
        说明：在第一次调用QueryByUserid时会后端会返回一个null，此时前端调用该API将用户具体信息初始化
        #### 传入参数
        变量名|类型|说明
        -|-|-  
        token | String | 用户唯一标识
        #### 返回参数（UserEntity）
    * ### URL：/UserDetail/QueryByUserid
        #### 传入参数
        变量名|类型|说明
        -|-|-  
        token | String | 用户唯一标识
        #### 返回参数（UserEntity）
    * ### URL：/UserDetail/UpdateByUserid
        说明：用于更新用户详细信息，除了token其他所有参数都可以为空，默认不修改
        #### 传入参数
        变量名|类型|说明
        -|-|-  
        token | String | 用户唯一标识
        avatar | multipartfile | 头像
        account | Double | 账户余额
        phone | String | 电话
        address | String | 收货地址
        nickname | String | 昵称
        #### 返回参数（UserEntity）
    * ### URL：/UserDetail/UpdateOldPassword
        #### 传入参数
        变量名|类型|说明
        -|-|-  
        token | String | 用户唯一标识
        oldpassword | String | 旧密码
        newpassword | String | 新密码
        #### 返回参数（UserEntity）
* ## CommentMicroservice port: 30010
    ### 评论信息 - Comment
        变量名          说明
        id              评论comment唯一标识符
        ownerId         评论创建者的id
        ownername       评论创建者的名字
        targetTicketId  评论的票品id
        content         内容
        replys          是一个url，前端通过这个url向后端索取回复本comment的reply
        createTime      评论创建的时间
    ### 回复信息 - Reply
        变量名          说明
        id              回复reply唯一标识符
        ownerId         回复创建者的id
        ownername       回复创建者的名字
        targetUserId    （被回复的reply|comment）的创建者id
        targetUsername  （被回复的reply|comment）的创建者姓名
        targetObjectId  （被回复的reply|comment）的id
        parentId        此reply回复的reply|comment
        type            "toReply"or"toComment"
        createTime      评论创建的时间
    ### response头中errorNum说明
        数字            说明
        0               成功
        1               尚未登录
        2               身份不对应
        3               账户被冻结

    * ### URL: /Comment/Add
        #### 传入参数
        变量名|类型|说明
        -|-|-
        token|String
        ticketid|Long|对此ticketid的票品进行评论
        content|String|评论的内容
        #### 返回参数 String="ok"
    * ### URL: /Comment/QueryByUserid
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String
        pagenumber|int|用于分页，因为用户可能对很多票品进行评论，所以需要进行评论
        #### 返回参数 (分了页的comment信息，一次9个)
    * ### URL: /Comment/QueryByTicketid
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        ticketid|Long|对此票的评论进行搜索
        pagenumber|int|用于分页
        #### 返回参数 (分了页的comment信息，一次9个)
    * ### URL: /Comment/UpdateContentByCommentid
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|Long|
        commentid|Long|需要修改的comment的id
        content|String|修改的新的内容(不能为空)
        #### 返回参数 (新的comment信息)
    * ### URL: /Comment/DeleteByCommentid
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String
        commentid|Long|需要删除的comment的id
        #### 返回参数 (删除掉的comment信息)
    * ### URL: /Reply/AddToComment
        #### 说明：对评论进行回复（即 reply to comment）
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String
        commentid|Long|要回复的评论的id
        content|String|回复的内容（不能为空）
        #### 返回参数 String=“ok”
     * ### URL: /Reply/AddToReply
        #### 说明：对回复进行回复（即 reply to reply1）
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String
        replyid|Long|被回复的回复（即reply1）的id
        content|String|回复的内容
        #### 返回参数 String=“ok”
    * ### URL: /Reply/QueryByParentId
        #### 说明：由于我们的评论要做成树状，设计为comment->reply，comment为reply的parent
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        parentid|Long|为父comment的id，对这个comment的回复进行搜索
        type|String|"toComment"表示parent是一个comment；“toReply”表示parent是一个reply
        pagenumber|int|进行分页操作（一页4个）
        #### 返回参数 （分好页的reply信息）
    * ### URL: /Reply/QueryByReplyId
        #### 说明：对这个reply，搜索回复它的replys
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        replyid|Long|此reply的id，用来get回复它的回复
        pagenumber|int|分页pagenumber
        #### 返回参数 （分好页的reply信息）
    * ### URL: /Reply/Delete
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String|
        replyid|Long|要删除的回复的id
        #### 返回参数 String=“ok“
* ## OrderMicroService port:30011
    说明：在下订单的过程中前端先调用userdetail的api获取收货地址，手机号等默认信息，展现订单生成页面，并允许用户填写自定义信息，然后生成订单并等待用户付款
    ### response头中errorNum说明
        数字            说明
        0               成功
        1               尚未登录
        2               身份不对应
        3               账户被冻结
    ### 订单信息
        变量名          说明
        Id         订单唯一标识符
        userId          用户唯一标识符
        receiver        接收人名称
        phone           电话
        address         收货地址
        status          状态（取消中，已取消，退款中，已退款，代付款，代发货，已发货，已过期，已删除，已发货，已签收）
        ordertime       下订单时间（超过1天则视为过期）
        items           下单对应的购买物品
    ### 物品信息
        变量名             说明
        Id                  订单中物品唯一标识符
        ticketId            票品唯一标识符
        price               价格
        number              数量
        date                日期
        image               图片url
        title               标题
        venue               地址
        city                城市
        status              购买操作结果（未操作，成功，失败） 
    * ### URL: /Order/QueryByUserid
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String|
        pagenumber|int|
        #### 返回参数(orderEntity)
    * ### URL：/Order/QueryByOrderid
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String|
        orderid|Long|订单唯一标识
        #### 返回参数(orderEntity)
    * ### URL：/Order/AddInDetailPage
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String|
        ticketid | Long | 票品唯一标识符
        price | Double | 价格
        date | String | 日期
        number | Long | 数字
        recerver | String | 接收人姓名
        phone | String | 电话
        address | String | 收货地址
        #### 返回参数(orderEntity)
    * ### URL：/Order/AddBatchInCart
        #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String|
        cartids | cartid数组，格式为[1,2,3] | 购物车中选中的物品
        receiver | String | 接收人姓名
        phone | String | 电话
        address | String | 收货地址
        #### 返回参数(orderEntity)
    * ### URL：/Order/Buy
         #### 传入参数
        变量名|类型|说明
        -|-|-    
        token|String|
        orderid | Long | 订单唯一标识符
        #### 返回参数（hashmap）
        键|说明
        -|-
        message | expired代表过期 Insufficient balance代表用户余额不足 success代表成功
        Iventory shortage | 因缺货而购买失败的物品列表
    * ## CollectionMicroService port:30012
        ### response头中errorNum说明
            数字            说明
            0               成功
            1               尚未登录
            2               身份不对应
            3               账户被冻结
        ### 收藏信息
            变量名          说明
            id              收藏票品唯一标识符
            userId          收藏票品所属用户
            ticketId        票品唯一标识符
            image           图片的url
            title           主题
            intro           简要介绍
        * ### URL：/Collection/Save
            #### 传入参数
            变量名|类型|说明
            -|-|-       
            token | String | 用户标识
            ticketid | String | 票品标识
            #### 返回参数（String）
        * ### URL：/Collection/DeleteBatch
            #### 传入参数
            变量名|类型|说明
            -|-|-       
            token | String | 用户标识
            batchcollectionid | Array | 要批量删除的收藏票品id（注意不是票品id）
            #### 返回参数（String）
        * ### URL：/Collection/QueryByUserId
            #### 传入参数
            变量名|类型|说明
            -|-|-       
            token | String | 用户标识
            pagenumber | int | 页数
            #### 返回参数（分页了的收藏）
        * ### URL：/Collection/Share
            #### 传入参数
            变量名|类型|说明
            -|-|-       
            token | String | 用户标识
            
