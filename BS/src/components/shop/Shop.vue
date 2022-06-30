<template>
  <div class="app-container">
    <div class="filter-container">
       <el-col :span="6" style="margin-right:30px">
          <el-input clearable placeholder="请输入要搜索商品名" v-model="queryInfo"  @clear="searchShopByName()" @blur="searchShopByName">
            <template #append>
              <el-button :icon="Search"  @click="searchShopByName" />
              <!--<i class="iconfont icon-search"></i>-->
            </template>
          </el-input>
        </el-col>

        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible=true">添加商品</el-button>
        </el-col>
      <!-- <el-form>
        <el-form-item>
          <el-button type="success" icon="plus"  @click="this.addDialogVisible=true">添加商品
          </el-button>
        </el-form-item>
      </el-form> -->
    </div>
    <el-table :data="Allshops" v-loading="listLoading"  border fit
              highlight-current-row>
      <el-table-column align="center" label="商品编号" width="60">
          <template #default="scope">
          <span v-text="getIndex(scope.row.id)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="商品名称" prop="roleName" width="300">
                  <template #default="scope">
           <div v-text="scope.row.name" style="display: inline-block;vertical-align: middle;"></div>
           </template>
      </el-table-column>
      <el-table-column align="center" label="商品图片" width="200" height="150">
          <template #default="scope">
          <!-- <div v-for="shop in scope.row"> -->
            <el-image :src="BASE_URL+((scope.row.img)==''?'/image/wrong.jpg':scope.row.img)">


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
            <div v-text="scope.row.price" style="display: inline-block;vertical-align: middle;"></div>
        </template>
      </el-table-column>
        <el-table-column align="center" label="更新时间" prop="roleName" width="250">
            <template #default="scope">
            <!-- <div v-text="scope.row.firsttime" style="display: inline-block;vertical-align: middle;"></div>
           -->
         <el-date-picker
    v-model="scope.row.firsttime"
    type="datetime" >
</el-date-picker>
        </template>

        </el-table-column>
        <el-table-column align="center" label="描述" prop="roleName" width="300">
                                  <template #default="scope">
            <div v-text="scope.row.content" style="display: inline-block;vertical-align: middle;"></div>
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
                       @click="delGoodById(scope.row.id)">
              删除
            </el-button>
        </template>
      </el-table-column>
    </el-table>

      <!-- <el-pagination
          v-model:currentPage="fpage.pagenum"
          v-model:page-size="fpage.pagesize"
          :page-sizes="[1, 2, 3, 5]"
          :small=true
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      /> -->
  <el-pagination
  background
  layout="prev, pager, next"
  v-model:currentPage="fpage.pagenum"
  :page-size="fpage.pagesize"
  :total="fpage.total"
  @current-change="handleCurrentChange"
  >
  </el-pagination>


<div>
    <el-dialog        
        v-model="updateDialogVisible" title="修改商品"
        width="30%"
        @close="updateGoodClose">
  <el-form :model="form">
    <el-form-item label="商品名称" :label-width="formLabelWidth">
      <el-input v-model="updateGood.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="商品金额" :label-width="formLabelWidth">
  <el-input v-model="updateGood.price" autocomplete="off"></el-input>

    </el-form-item>
    <el-form-item label="商品描述" :label-width="formLabelWidth">
  <el-input v-model="updateGood.content" autocomplete="off"></el-input>

    </el-form-item>

<el-form-item label="商品图片" :label-width="formLabelWidth">
         <el-upload
                class="avatar-uploader"
                action="http://127.0.0.1:8081/upload"

                :show-file-list="false"
                :on-success="updatehandleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="updateGood.img" :src="BASE_URL+updateGood.img" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
</el-form-item>
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
    <el-form-item label="商品名称" :label-width="formLabelWidth">
      <el-input v-model="addGood.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="商品金额" :label-width="formLabelWidth">
  <el-input v-model="addGood.price" autocomplete="off"></el-input>

    </el-form-item>

        <el-form-item label="商品描述" :label-width="formLabelWidth">
  <el-input v-model="addGood.content" autocomplete="off"></el-input>

    </el-form-item>

         <el-form-item label="属于的商家" :label-width="formLabelWidth">
  <el-input v-model="addGood.sellerid" autocomplete="off"></el-input>

    </el-form-item>

    
    

<el-form-item label="商品图片" :label-width="formLabelWidth">
         <el-upload
                class="avatar-uploader"
                action="http://127.0.0.1:8081/upload"

                :show-file-list="false"
                :on-success="addhandleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="addGood.img" :src="BASE_URL+addGood.img" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
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
import {Search,Edit,Delete} from '@element-plus/icons-vue'
import { getCurrentInstance } from "vue";
  export default {
  setup() {
    return {
      Search, Edit, Delete
    }
  },
    data() {
      return {
        fpage:{
        pagenum: 1,
        pagesize: 4,
        total:'',
        },
        queryInfo:'',
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
        Allshops:[],
        updateGood:{
          id:'',
          name: '',
          price: '',
          img: '',
          content:''
        },
        addGood:{
          id:'',
          name: '',
          price: '',
          img: '',
          content:'',
          sellerid:'',
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
     this.getAllGoods()
    this.getfpageInfo()
    },
    methods: {

      handleSizeChange(pagesize) {
      // console.log(pagesize)
      this.fpage.pagesize = pagesize
      //this.getUserList()
    },
    handleCurrentChange(pagenum) {
       console.log(pagenum)
      this.fpage.pagenum = pagenum
      this.getfpageInfo()
      //this.getUserList()
    },
  async getfpageInfo(){
          let {data:res} = await this.$http.get('/shop_admin/fpage/'+eval((this.fpage.pagenum-1)*this.fpage.pagesize)+"/"+this.fpage.pagesize)
          console.log(res)
          this.Allshops=res.data
          this.getAllGoods()
  }
    ,
     async getAllGoods() {
        //查询所有权限
      let {data:res} = await this.$http.get('/shop_admin/getAllshops')

      this.fpage.total=res.data.length
      // console.log(this.fpage.total)
      // console.log(this.Allshops)
      },
    
      getIndex($index) {
        //表格序号
        return $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempRole.roleName = '';
        this.tempRole.roleId = '';
        this.tempRole.permissions = [];
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate(row) {
        console.log(row)
        let Good = row
        //console.log(Good)
        this.updateGood.name = Good.name;
        this.updateGood.price = Good.price;
        this.updateGood.img=Good.img;
        this.updateGood.id=Good.id;
        this.updateGood.content=Good.content;
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
         let {data:res}= await this.$http.delete('/shop_admin/'+id)
         console.log(res)
         if(res.data>0){
        this.$message.success("删除商品成功！");
         }else{
           this.$message.error("删除商品失败！");
         }
         this.g
     //this.getAllGoods()  
     this.getfpageInfo()
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
          let {data:res}= await this.$http.put('/shop_admin/',this.updateGood)
          console.log(res)
           if(res.data>0){
        this.$message.success("修改商品成功！");
        this.updateDialogVisible=false
         }else{
           this.$message.error("修改商品失败！");
         }
    // this.getAllGoods()  
    this.getfpageInfo()
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
      //this.getAllGoods()
      this.getfpageInfo()
      },
      async searchShopByName(){
        let {data:res} = await this.$http.get('/shop_admin/getSim/'+this.queryInfo)
        if(this.queryInfo==''){
          //this.getAllGoods()
          this.getfpageInfo()
        }
        console.log(res)
      this.Allshops=res.data
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
