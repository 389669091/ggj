let vm = new Vue({
    el:'.page-content',
    data:function(){
        return {
            nodes:[],
            setting:{//树配置对象  用于设置树结构全局配置
                data:{
                    simpleData: {//简单数据格式设置
                        enable:true,   //开启简单数据格式   自动将一维数组组装成父子结构
                        pIdKey: 'parentId'  //默认的父id属性名为pId
                    }
                },
                callback:{//回调函数设置，用于设置一些事件回调函数
                    onClick:this.onClick
                }
            },

        }
    },
    methods:{
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
                let zTreeObj = $.fn.zTree.init($('#select-tree'),this.setting,this.nodes);
            }).catch(error=>{
                layer.msg(error.message);
            });

        },
        onClick:function(event,treeId,treeNode){//点击事件触发的函数
            parent.layer.parentName=treeNode.name;//名字用于显示
            parent.layer.parentId=treeNode.id;
            parent.layer.parentIds=treeNode.parentIds+treeNode.id+',';   //  父区域的parentIds+父区域id+','
            let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
            console.log(parent.layer.parentName)
        }
    },
    mounted:function () {//当vue的代理el节点挂载上去后自动执行
        this.initTree();
    }
});