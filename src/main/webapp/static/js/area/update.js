let vm = new Vue({
    el:'.page-content',
    data:{
        area:{}
    },
    methods:{
        doUpdate:function () {
            axios({
                url:`manager/area/doUpdate`,   //基于base的绝对路径请求
                method:'put',
                data:this.area
            }).then((response)=>{//剪头函数  自动传递上下文的this
                // console.log(response.data);
                //成功后关闭当前子窗口  提示
                //注意：parent 是 JS 自带的全局对象，可用于操作父页面
                let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.success = response.data.success;
                if(response.data.success){//成功更新，关闭当前子窗口，并且通过父窗口提示
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);

                }else{
                    layer.msg(response.data.msg);//子窗口中提示
                }

                //失败后提示
            }).catch(error=>{
                // console.log(error);
                layer.msg(error.message);
            })
        },
        toModule:function () {//弹出选择图标子窗口
            /*弹出一个 iframe窗*/
            layer.open({
                type: 2,        //加载一个页面
                title: false,
                area: ['100%', '100%'],//设置宽高   px  或比例  占据父窗口
                content: 'manager/area/toModule',
                end:()=>{ //关闭子窗口后的回调函数  会把this替换掉
                    //判断layer.icon是否有值
                    if(layer.icon!=undefined&&layer.icon!=''){
                        this.area.icon=layer.icon;  //获取从子窗口modules传递的icon
                    }
                }
            });
        },
        toSelect:function () {
            layer.open({
                type: 2,        //加载一个页面
                title: false,
                area: ['100%', '100%'],//设置宽高   px  或比例  占据父窗口
                content: 'manager/area/toSelect',
                end:()=>{ //关闭子窗口后的回调函数  会把this替换掉
                    //判断layer.parentName是否有值
                    if(layer.parentName!=undefined&&layer.parentName!=''){
                        this.area.parentName=layer.parentName;  //获取从子窗口select传递的parentName
                        //获取子窗口传来的父id和和父ids，实现
                        this.area.parentId=layer.parentId;
                        this.area.parentIds=layer.parentIds;
                    }
                }
            });
        }
    },
    created:function () {  //data初始化后调用
        //获取父窗口parent的layer对象
        // console.log(parent);
        this.area=parent.layer.obj;
        //获取父区域原来的ids
        this.area.oldParentIds=this.area.parentIds; //更新前一致
        console.log(this.area)
    }
});