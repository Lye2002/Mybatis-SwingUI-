<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="success" icon="plus"  @click="this.addDialogVisible=true">添加订单
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="AllOrders" v-loading="listLoading"  border fit
              highlight-current-row>
      <!-- <el-table-column align="center" label="序号" width="60">
          <template #default="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column> -->
   <el-table-column align="center" label="订单号" prop="roleName" width="100">
                  <template #default="scope">
           <div v-text="scope.row.orderid" style="display: inline-block;vertical-align: middle;"></div>
           </template>
      </el-table-column>

      <el-table-column align="center" label="商品名称" prop="roleName" width="200">
                  <template #default="scope">
           <div v-text="scope.row.shops_detail.name" style="display: inline-block;vertical-align: middle;"></div>
           </template>
      </el-table-column>
      <el-table-column align="center" label="商品图片" width="200" height="150">
          <template #default="scope">

          <!-- <div v-for="shop in scope.row"> -->
            <el-image :src="BASE_URL+((scope.row.shops_detail.img)==''?'/image/wrong.jpg':scope.row.shops_detail.img)">


              <div slot="placeholder" class="image-slot">
                加载中<span class="dot">...</span>
              </div>
            </el-image>
       

            <!-- <div v-text="scope.row.img" style="display: inline-block;vertical-align: middle;"></div> -->
          <!-- </div> -->
        </template>
      </el-table-column>
      <el-table-column align="center" label="商品价格" prop="roleName" width="100">
                  <template #default="scope">
            <div v-text="scope.row.shops_detail.price" style="display: inline-block;vertical-align: middle;"></div>
        </template>
      </el-table-column>
        <el-table-column align="center" label="购买时间" prop="roleName" width="250">
            <template #default="scope">
            <!-- <div v-text="scope.row.firsttime" style="display: inline-block;vertical-align: middle;"></div>
           -->
         <el-date-picker
    v-model="scope.row.buytime"
    type="datetime" >
</el-date-picker>
        </template>

        </el-table-column>
        <el-table-column align="center" label="地址" prop="roleName" width="200">
                                  <template #default="scope">
            <div v-text="scope.row.address" style="display: inline-block;vertical-align: middle;"></div>
        </template>
        </el-table-column>

         <el-table-column align="center" label="购买用户账号" prop="roleName" width="170">
                  <template #default="scope">
           <div v-text="scope.row.username" style="display: inline-block;vertical-align: middle;"></div>
           </template>
      </el-table-column>

        <el-table-column align="center" label="归属商家" prop="roleName" width="170">
                                  <template #default="scope">
            <div v-text="scope.row.sellerid" style="display: inline-block;vertical-align: middle;"></div>
        </template>
        </el-table-column>
      <el-table-column align="center" label="管理" width="220">
          <template #default="scope">
            <el-button type="primary" icon="el-icon-edit" @click="showUpdate(scope.row)" >修改
            </el-button>
            <el-button type="danger"
                       icon="el-icon-delete"
                       @click="delGoodById(scope.row.orderid)">
              删除
            </el-button>
        </template>
      </el-table-column>
    </el-table>


<div>
    <el-dialog        
        v-model="updateDialogVisible" title="修改商品"
        width="30%"
        @close="updateGoodClose">
  <el-form :model="form">

    <el-form-item label="商品订单号" :label-width="formLabelWidth">
      <el-input v-model="updateOrder.orderid" autocomplete="off" disabled></el-input>
    </el-form-item>
    <el-form-item label="购买商品号" :label-width="formLabelWidth">
  <el-input v-model="updateOrder.shopid" autocomplete="off"></el-input>

    </el-form-item>
    <el-form-item label="购买用户账号" :label-width="formLabelWidth">
  <el-input v-model="updateOrder.username" autocomplete="off"></el-input>
    </el-form-item>
        <el-form-item label="收货地址" :label-width="formLabelWidth">
  <el-input v-model="updateOrder.address" autocomplete="off"></el-input>
    </el-form-item>



<!-- <el-form-item label="商品图片" :label-width="formLabelWidth">
         <el-upload
                class="avatar-uploader"
                action="http://127.0.0.1:8081/upload"

                :show-file-list="false"
                :on-success="updatehandleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="updateGood.img" :src="BASE_URL+updateGood.img" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
</el-form-item> -->
  </el-form>
  <div slot="footer" class="dialog-footer" style="position:center">
  
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="comfirUpdate">确 定</el-button>
  </div>
</el-dialog>
</div>



<el-dialog        
        v-model="addDialogVisible" title="新增商品"
        width="30%"
        @close="addGoodClose">
  <el-form :model="form">
    <!-- <el-form-item label="订单号" :label-width="formLabelWidth">
      <el-input v-model="addOrder.orderid" autocomplete="off"></el-input>
    </el-form-item> -->
    <el-form-item label="商品编号" :label-width="formLabelWidth">
  <el-input v-model="addOrder.shopid" autocomplete="off"></el-input>

    </el-form-item>

        <el-form-item label="购买用户" :label-width="formLabelWidth">
  <el-input v-model="addOrder.username" autocomplete="off"></el-input>

    </el-form-item>

         <el-form-item label="送达地址" :label-width="formLabelWidth">
  <el-input v-model="addOrder.address" autocomplete="off"></el-input>

    </el-form-item>

  </el-form>
  <div slot="footer" class="dialog-footer" style="position:center">
  
    <el-button @click="addDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="comfirAdd">确 定</el-button>
  </div>
</el-dialog>


  </div>
</template>
<script>
//import BASE_URL from "@/main.js"
import { getCurrentInstance } from "vue";
  export default {
    name:'Orders',
    data() {
      return {
        BASE_URL:getCurrentInstance().appContext.config.$BASE_URL,
        list: [],//表格的数据
        allPermission: [],
        listLoading: false,//数据加载等待动画
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '新建角色'
        },
        shop: {
          name: '',
          price: '',
          imgurl: '',
        },
        adminName: '管理员',
        AllOrders:[],
        updateOrder:{
          orderid:'',
          shopid: '',
          username:'',
          address:'',
        },
        addOrder:{
          orderid:'',
          shopid: '',
          username:'',
          address:'',
        },
    
        updateDialogVisible:false,
        formLabelWidth: '120px',
        addDialogVisible:false
      }
    },
    created() {
      //this.getList();
     // this.getAllPermisson();
     //console.log(this.BASE_URL)
     this.getAllOrders()
    },
    methods: {
     async getAllOrders() {
        //查询所有权限
      let {data:res} = await this.$http.get('/orders_admin')
      console.log(res)
      this.AllOrders=res.data
      console.log(this.AllOrders)
      },
      getIndex($index) {
        //表格序号
        return $index + 1
      },
      showUpdate(info) {
        console.log(info)
        // let Good = this.AllOrders[index];
        // console.log(this.AllOrders[index])

        this.updateOrder.orderid = info.orderid;
        this.updateOrder.shopid = info.shopid;
        this.updateOrder.username=info.username;
        this.updateOrder.address=info.address;
        this.dialogStatus = "update"
        this.updateDialogVisible = true
      },

      checkRoleNameUnique(roleId) {
        //校验名称重复
        let roleName = this.tempRole.roleName;
        if (!roleName) {
          this.$message.error("请填写角色名称");
          return false;
        }
        let roles = this.list;
        let result = true;
        for (let j = 0; j < roles.length; j++) {
          if (roles[j].roleName === roleName && (!roleId || roles[j].roleId !== roleId  )) {
            this.$message.error("角色名称已存在");
            result = false;
            break;
          }
        }
        return result;
      },
      async delGoodById(id) {
         let {data:res}= await this.$http.delete('/orders_admin/'+id)
         console.log(res)
         if(res.data>0){
        this.$message.success("删除订单成功！");
         }else{
           this.$message.error("删除订单失败！");
         }
     this.getAllOrders() 
      },
      checkAll(_index) {
        //点击菜单   相当于全选按钮
        let v = this;
        if (v.isMenuAll(_index)) {
          //如果已经全选了,则全部取消
          v.noPerm(_index);
        } else {
          //如果尚未全选,则全选
          v.allPerm(_index);
        }
      },
      allPerm(_index) {
        //全部选中
        let menu = this.allPermission[_index].permissions;
        for (let j = 0; j < menu.length; j++) {
          this.addUnique(menu[j].id, this.tempRole.permissions)
        }
      },
      noPerm(_index) {
        //全部取消选中
        let menu = this.allPermission[_index].permissions;
        for (let j = 0; j < menu.length; j++) {
          let idIndex = this.tempRole.permissions.indexOf(menu[j].id);
          if (idIndex > -1) {
            this.tempRole.permissions.splice(idIndex, 1);
          }
        }
      },
      addUnique(val, arr) {
        //数组内防重复地添加元素
        let _index = arr.indexOf(val);
        if (_index < 0) {
          arr.push(val);
        }
      },
      checkRequired(_perm, _index) {
        //本方法会在勾选状态改变之后触发
        let permId = _perm.id;
        if (this.tempRole.permissions.indexOf(permId) > -1) {
          //选中事件
          //如果之前未勾选本权限,现在勾选完之后,tempRole里就会包含本id
          //那么就要将必选的权限勾上
          this.makeReuqiredPermissionChecked(_index);
        } else {
          //取消选中事件
          if (_perm.requiredPerm === 1) {
            //如果是必勾权限,就把本菜单的权限全部移出
            //(其实也可以提示用户本权限是菜单里的必选,请先取消勾选另外几个权限,交互太麻烦,此处就直接全部取消选中了)
            this.noPerm(_index);
          }
        }
      },
      updatehandleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
        console.log(this.imgurl)
        console.log(res.uploadedFilePath)
        let localfile=res.uploadedFilePath+""
              console.log(typeof(localfile))
        console.log((localfile.indexOf("U")))
        let rr=localfile.lastIndexOf('\\')
        

        this.updateGood.img='/image'+localfile.substring(localfile.lastIndexOf('\\',rr))
        //this.updateGood.img=this.updateGood.img.replaceAll(/\\/,'/')
        this.updateGood.img=this.updateGood.img.replaceAll('\\','/')


         console.log(this.updateGood.img)
        //\image\a073ebb5-58ad-4401-abfd-970df8ad9be6.jpg
        //C:\Users\Lye\Desktop\shop\src\main\resources\static\image\a073ebb5-58ad-4401-abfd-970df8ad9be6.jpg
      },
    addhandleAvatarSuccess(res,file){
          this.imageUrl = URL.createObjectURL(file.raw);
        console.log(this.imgurl)
        console.log(res.uploadedFilePath)
        let localfile=res.uploadedFilePath+""
              console.log(typeof(localfile))
        console.log((localfile.indexOf("U")))
        let rr=localfile.lastIndexOf('\\')
        

        this.addGood.img='/image'+localfile.substring(localfile.lastIndexOf('\\',rr))
        //this.updateGood.img=this.updateGood.img.replaceAll(/\\/,'/')
        this.addGood.img=this.addGood.img.replaceAll('\\','/')


         console.log(this.addGood.img)

},

      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
     async comfirUpdate(){
      console.log(this.updateGood)
          let {data:res}= await this.$http.put('/orders_admin/',this.updateOrder)
          console.log(res)
           if(res.data>0){
        this.$message.success("修改商品成功！");
        this.updateDialogVisible=false
         }else{
           this.$message.error("修改商品失败！");
         }
     this.getAllGoods()  
      },
      async comfirAdd(){
      console.log(this.addGood)
      let {data:res} = await this.$http.post('/shop_admin',this.addGood)
      console.log(res)
      if(res.data>0){
        this.$message.success("增加商品成功！");
        this.addDialogVisible=false
      }else{
          this.$message.error("增加商品失败！");
      }
      this.getAllOrders()
      }

    }
  }
</script>
<style scoped>
  .requiredPerm {
    color: #ff0e13;
  }


    .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
