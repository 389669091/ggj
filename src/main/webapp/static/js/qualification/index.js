// let vm = new Vue({
//     el:".page-content",
//     data:{
//         pageInfo:{
//             pageNum:1,
//             pageSize:5
//         },
//         condition:{
//             type:"",
//             check:""
//         }//查询条件对象
//     },
//     methods:{
//         _selectPage:function (pageNum,pageSize) {
//             this.pageInfo.pageNum=pageNum;
//             this.pageInfo.pageSize=pageSize;
//             this.selectPage();
//         },
//         selectPage:function () {
//             axios({
//                 url:`manager/qualification/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
//                 method:'post',
//                 data:this.condition
//             }).then(response=>{
//                 console.log(response.data.obj)
//                 this.pageInfo = response.data.obj;
//             }).catch(error=>{
//                 layer.msg(error.message);
//             })
//         },
//         selectAll:function () { //查询所有
//             this.condition={
//                 type:"",
//                 check:""};//清空条件
//             this._selectPage(1,this.pageInfo.pageSize);
//         }
//     },
//     created:function () {
//         this.selectPage();
//     }
// });


let vm=new Vue({

    el:".page-content",
    data:{
        pageInfo:{
            pageNum: 1,
            pageSize:5,
        },
        condition:{
            check:'',
            type:''
        }
    },
    methods:{
        _selectPage:function (pageNum,pageSize) {
            this.pageInfo.pageNum=pageNum;
            this.pageInfo.pageSize=pageSize;
            this.selectPage()
        },
        selectPage:function () {
           axios({
               url:`manager/qualification/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
               method:'post',
               data:this.condition
           }).then((response)=>{
               console.log(response.data.obj);
               this.pageInfo=response.data.obj
           }).catch(error=>{
               layer.msg(error.message);
           })
        },
        selectAll:function () { //查询所有
            this.condition={
                type:"",
                check:""};//清空条件
            this._selectPage(1,this.pageInfo.pageSize);
        },
        toUpdate:function (qualification) {
            //根据id从后台接口获取动态的address的目录
            axios({
                url:`manager/qualification/getPath/${qualification.uploadUserId}`
            }).then(response=>{
                //response.data.obj得到路径   //this.qualification.address图片文件名
                qualification.address=response.data.obj+"/"+qualification.address;
                //借助当前窗口的layer对象传递值到子窗口
                layer.obj = qualification;
              
                layer.open({
                    type: 2,
                    title: false,
                    area: ['60%', '80%'],
                    content: 'manager/qualification/toUpdate',
                    end: () => { //关闭子窗口后的回调函数  会把this替换掉


                        this.selectPage();//刷新页面

                    }
                });

            }).catch(error=>{
                layer.msg(error.message)
            })
        },
    },
    created:function () {
        this.selectPage()
    }
});