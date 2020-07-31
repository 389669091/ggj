let vm = new Vue({
    el:'.page-content',
    data:function(){
        return {
            pageInfo:{
                //初始化  第一次页面加载的时候使用
                pageNum:1,
                pageSize:5

            },
            name:'',
            pid:'',
            nodes:[],
            setting:{//树配置对象  用于设置树结构全局配置
                data:{
                    simpleData: {//简单数据格式设置
                        enable:true,   //开启简单数据格式   自动将一维数组组装成父子结构
                        pIdKey: 'parentId'  //默认的父id属性名为pId
                    }
                },
                callback:{//回调函数设置，用于设置一些事件回调函数
                    onClick:this.onClick,
                    beforeEditName:this.beforeEditName, //在点击修改按钮的时候触发，返回false则阻止进入编辑节点
                    beforeRemove:this.beforeRemove
                },
                edit:{
                    enable: true,
                    renameTitle:"修改",
                    removeTitle:"删除"
                },
                // edit:{
                //     enable:true,
                //     renameTitle:'修改',
                //     removeTitle:'删除'
                // },
                view:{
                    addHoverDom:this.addHoverDom,   //鼠标移动到树节点上触发
                    removeHoverDom:this.removeHoverDom //鼠标离开节点触发
                }
            },

        }
    },
    methods:{

        selectPage:function(){
            if(this.pid!=''){//根据pid查询所有子区域
                /*根据区域名查询*/
                //发送ajax请求返回统一结果响应数据  {success:xxx,msg:xxx,obj:xxx}
                axios({
                    url:`manager/area/selectByPid/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,   //基于base的绝对路径请求
                    params:{id:this.pid}
                }).then((response)=>{//剪头函数  自动传递上下文的this
                    this.pageInfo = response.data.obj;
                }).catch(error=>{
                    console.log(error);
                })
            }else{
                /*根据区域名查询*/
                //发送ajax请求返回统一结果响应数据  {success:xxx,msg:xxx,obj:xxx}
                axios({
                    url:`manager/area/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,   //基于base的绝对路径请求
                    params:{name:this.name}
                }).then((response)=>{//剪头函数  自动传递上下文的this
                    this.pageInfo = response.data.obj;
                    console.log(this.pageInfo)
                }).catch(error=>{
                    console.log(error);
                })
            }


        },
        _selectPage:function (pageNum,pageSize) {
            this.pageInfo.pageNum=pageNum;
            this.pageInfo.pageSize=pageSize;
            this.selectPage();
        },
        selectAll:function(){
            this.pageInfo={
                //初始化  第一次页面加载的时候使用
                pageNum:1,
                pageSize:5
            };
            this.name='';
            this.pid='';
            this.selectPage();

        },
        toUpdate:function(obj){
            //借助当前窗口的layer对象传递值到子窗口
            layer.obj = obj;
            // console.log(window);
            /*弹出一个 iframe窗*/
            layer.open({
                type: 2,        //加载一个页面
                title: false,
                area: ['80%', '80%'],//设置宽高   px  或比例  占据父窗口
                content: 'manager/area/toUpdate',
                end:()=>{ //关闭子窗口后的回调函数  会把this替换掉
                    // layer.msg(this)
                    /* console.log(this)*/
                    // console.log(layer);
                    if(layer.success==undefined||!layer.success){//直接关闭子窗口未更新或更新失败
                        this.selectPage();
                    }
                }
            });
        },
        download:function(){
            //文件下载
            location.href="manager/area/download"
        },
        upload:function(event){
            /**
             * 1.创建表单对象FormData
             2.获取点击的事件源中的文件对象
             3.表单对象添加文件对象file
             4.设置提交请求content-type为multipart/form-data  method必须为post
             5.发送ajax请求
             * @type {FormData}
             */
            // let formData = new FormData();
            // // console.log(event.target.files[0]);
            // //file参数名（必须与后台接口参数名一致） 值是文件对象
            // formData.append("file",event.target.files[0]);
            // axios({
            //     url:`manager/area/upload`,   //基于base的绝对路径请求
            //     method:'post',
            //     headers:{'Content-Type':'multipart/form-data'},//设置请求头的请求体类型为文件上传格式
            //     data:formData
            // }).then((response)=>{//剪头函数  自动传递上下文的this
            //     layer.msg(response.data.msg);
            //     this.selectPage();
            // }).catch(error=>{
            //     layer.msg(error.message);
            // })
            let formData=new FormData();
            formData.append("file",target.files[0]);
            axios({
                url:`manager/area/upload`,
                method:"post",
                //设置请求头
                header:{'Content-type':'multipart/form-data'},
                data:formData,
            }).then(response=>{
                layer.msg(response.data.msg);
                    this.selectPage();
                }).catch(error=>{
                    layer.msg(error.message);
            })
        },
        doDelete:function (id) {

            layer.msg("确认删除吗?", {
                time: 0 //不自动关闭
                , btn: ['是', '否']
                , yes: (index) => {
                    let area = {id: id,delFlag: 1};
                    layer.close(index);
                    axios({
                        url: `manager/area/doDelete`,
                        method: 'put',
                       data:area
                    }).then((response) => {
                        console.log(response.id);
                        console.log(response);
                        if (response.data.success) {
                            this.selectPage();
                            this.initTree()
                        }
                        layer.msg(response.data.msg);
                    }).catch(error => {
                        // console.log(error);
                        layer.msg(error.message);
                    })
                }
            })
        },
        initTree:function () {
            axios({
                url:'manager/area/select'
            }).then(response=>{
                this.nodes=response.data.obj;
                //动态添加一个父节点
                this.nodes[this.nodes.length]={id:0,name:'区域列表',open:true};

                /*init(obj,setting,nodes)初始化树的api
                   * obj是需要挂载树的jq节点对象
                   * setting是树配置对象
                   * nodes：树的显示节点信息数组
                   * */
                //必须保证响应返回对nodes赋值后，再初始化树
                let zTreeObj = $.fn.zTree.init($('#treeMenu'),this.setting,this.nodes);
            }).catch(error=>{
                layer.msg(error.message);
            });

        },
        onClick:function(event,treeId,treeNode){//点击事件触发的函数
            this.pid=treeNode.id;
            this.selectPage();
        },
        beforeEditName:function (treeId,treeNode) {
            //阻止默认编辑节点状态  弹出更新窗口
            layer.open({
                type: 2,        //加载一个页面
                title: false,
                area: ['80%', '90%'],//设置宽高   px  或比例  占据父窗口
                content: 'manager/area/toUpdate',
                /*end:()=>{ //关闭子窗口后的回调函数  会把this替换掉

                }*/
            })
            return false;
        },
        beforeRemove:function (treeId,treeNode) {//根据返回值false则阻止删除节点  true则会将节点删除
            console.log("阻止删除")
            return false;
        },
        addHoverDom:function (treeId,treeNode) {
            let tId = treeNode.tId;
            console.log(tId)
            //1.获取span节点
            let aObj = $(`#${tId}_span`);
            let spanObj = $(`#${tId}_add`);
            if(spanObj.length>0){return}//已存在，阻止创建
            //2.获取tId值，组装  新增的span标签
            let span = `<span class="button add" id="${tId}_add" title="添加"  ></span>`

            //3.插入到显示节点名的dom后面
            aObj.after(span);
            //4.获取新增节点，绑定点击事件
            // console.log(this)
            $(`#${tId}_add`).on('click',function () {
                console.log("------------------");
                /*layer.open({
                    type: 2,        //加载一个页面
                    title: false,
                    area: ['80%', '90%'],//设置宽高   px  或比例  占据父窗口
                    content: 'manager/area/toUpdate',
                    /!*end:()=>{ //关闭子窗口后的回调函数  会把this替换掉

                    }*!/
                })*/
            });

            // console.log(treeNode)
        },
        removeHoverDom: function (treeId,treeNode) {
            // console.log(treeNode+"移出")
            //解除节点的绑定事件  和删除节点
            $(`#${treeNode.tId}_add`).unbind().remove();
        }
    },
    mounted:function () {//当vue的代理el节点挂载上去后自动执行
        this.initTree();
    },
    created:function () {  //data初始化后调用
        this.selectPage();
    }
});