// let vm=new Vue({
//    el:'.page-content',
//     data:{
//        workOrder:{}
//     },
//     methods:{
//         selectDetail:function (id) {
//             axios({
//                 url: `manager/admin/selectDetail`,   //基于base的绝对路径请求
//                 params:{id:id}
//             }).then((response)=>{
//                 this.workOrder=response.data.obj
//             }).catch(error=>{
//                 layer.msg(error.message)
//             })
//         }
//     },
//     created:function () {
//        //获取父窗口parent传来的参数（id）
//         this.selectDetail(parent.layer.obj)
//     }
// });



let vm = new Vue({
    el:'.page-content',
    data:{
        workOrder:{}
    },
    methods:{
        selectDetail:function (id) {
            axios({
                url:`manager/admin/selectDetail`,   //基于base的绝对路径请求
                params:{id:id}
            }).then((response)=>{//剪头函数  自动传递上下文的this

                this.workOrder=response.data.obj;
                console.log(this.workOrder)
            }).catch(error=>{
                // console.log(error);
                layer.msg(error.message);
            })
        }
    },
    created:function () {  //data初始化后调用
        //获取父窗口parent的layer对象
        // console.log(parent);
        this.selectDetail(parent.layer.obj);
    }
});

// let vm=new Vue({
//     el:'.page-content',
//     data:{
//         workOrder:{}
//     },
//     methods:{
//         selectDetail:function (id) {
//             axios({
//
//             })
//         }
//     },
//     created:function () {
//         this.selectDetail(parent.layer.obj)
//     }
// });