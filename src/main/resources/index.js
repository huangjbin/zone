// // 登录接口
// export const LOGIN_API = '/system_module/auth/login'
//
// // 获取左侧菜单列表
// export const GET_SIDEBAR_MENU_API = '/system_module/menu/findMenuList'
//
// // 创建定时任务
// export const CREATE_TIMING_TASK_API = '/quartz_module/task/addScheduleTask'
//
// //查询定时任务列表
// export const CHECK_TIMING_TASK_LIST_API = '/quartz_module/task/findTasks'
//
// //修改定时任务的状态（运行，暂停，删除）
// export const MODIFY_TIMING_TASK_STATUS_API = '/quartz_module/task/updateTaskScheduleStatusbyId'
//
// //修改定时器
// export const MODIFY_TIMING_TASK_API = '/quartz_module/task/updateScheduleTask'
//
// //查看定时任务详情
// export const SHOW_TIMING_TASK_DETAIL_API = '/quartz_module/task/findTaskbyId'
//
// //创建产品
// export const CREATE_PRODUCT_API = '/devmodule/product/create_prd_params'
//
// //查询产品列表,每页10条
// export const CHECK_PRODUCT_LIST_API = '/devmodule/product/search/page'
//
// //根据产品ID查找产品参数
// export const CHECK_PRODUCT_PARAM_API = '/devmodule/product_param/search'
//
// //根据产品ID获得产品实例，以及模糊查找产品接口，用于创建序列号获取产品名称使用
// export const CHECK_PRODUCT_API = '/devmodule/product/search'
//
// //编辑产品
// export const MODIFY_PRODUCT_API = '/devmodule/product/update_prd_params'
//
// //根据产品ID删除产品
// export const DELETE_PRODUCT_BY_ID_API = '/devmodule/product/delete'
//
// //创建序列号接口
// export const CREATE_SERIAL_API = '/devmodule/serial/create'
//
// //查询序列号列表,每页10条
// export const CHECK_SERIAL_NUMBER_LIST_API = '/devmodule/serial/search/page'
//
// //下载单个序列号二维码接口
// export const DOWNLOAD_SINGle_SERIAL_QRCODE_API = '/devmodule/serial/qrcode/download'
//
// // 创建设备所需调用产品相关信息
// export const CREATE_DEVICE_NEEDED_API = '/devmodule/product/search_create_parmas'
//
// // 创建设备
// export const CREATE_DEVICE_API = '/devmodule/device/dev_params_create/'
//
// //根据设备ID删除产品
// export const DELETE_DEVICE_BY_ID_API = '/devmodule/device/delete'
//
// //查询设备列表,每页10条
// export const CHECK_DEVICE_LIST_API = '/devmodule/device/search/page'
//
// //查询设备列表,每页10条,包含数据点
// export const CHECK_DEVICE_LIST_DATA_LENGTH_API = '/devmodule/device/infolist/page'
//
// //查询设备地图设备数据点接口
// export const CHECK_DEVICE_LAST_DATASTREAM_API = '/devmodule/device/search_last_datastream'
//
// //主动采集最新数据
// export const ACTIVE_ACQUISITION_LAST_DATA_API = '/devmodule/device/modbus2device'
//
// //设备上下线记录
// export const DEVICE_ONLINE_OR_OFFLINE_LIST_API = '/devmodule/device/status/page'
//
// //查看设备分组列表接口
// export const DEVICE_GROUPING_LIST_API = '/devmodule/devicegroup/search/page'
//
// //创建设备分组接口
// export const CREATE_DEVICE_GROUPING_API = '/devmodule/devicegroup/create'
//
// //查看设备分组详情页接口
// export const CHECK_DEVICE_GROUPING_DETAIL_API = '/devmodule/devicegroup/search'
//
// //修改/编辑设备分组接口
// export const MODIFY_DEVICE_GROUPING_BY_ID_API = '/devmodule/devicegroup/update'
//
// //删除设备分组接口
// export const DEL_DEVICE_GROUPING_API = '/devmodule/devicegroup/delete'
//
// //查看设备详情页：设备实例
// export const CHECK_DEVICE_DETAIL_API = '/devmodule/device/search'
//
// //查看设备详情页：设备参数（数据点）
// export const CHECK_DEVICE_DETAIL_PERAMS_API = '/devmodule/device_param/searchbydevid'
//
// //新增设备页面，通过序列号查找返回相关产品信息
// export const GET_PRODUCT_INFO_BY_SERIAL_API = '/devmodule/device/search_prod_by_devsno'
//
// //设备管理：修改设备
// export const MODIFY_DEVICE_API = '/devmodule/device/dev_params_update'
//
// //创建触发器调接口选设备
// export const CREATE_TRIGGER_TASK_NEEDED_API = '/devmodule/device/search_createtrigger_device'
//
// //创建触发器
// export const CREATE_TRIGGER_TASK_API = '/rule_module/tri/add'
//
// //查看触发器列表
// export const CHECK_TRIGGER_TASK_LIST_API = '/rule_module/tri/search'
//
// //删除触发器
// export const DEL_TRIGGER_TASK_API = '/rule_module/tri/dele'
//
// //修改触发器
// export const MODIFY_TRIGGER_TASK_API = '/rule_module/tri/update'
//
// //触发器详情
// export const CHECK_TRIGGER_TASK_DETAIL_API = '/rule_module/tri/search/single'
//
// //查看触发器告警记录列表
// export const CHECK_ALARM_RECORD_LIST_API = '/rule_module/log/search'
//
// //确认告警
// export const COMFORM_ALARM_API = '/rule_module/log/update'
//
// //创建角色调用拥有对应组织机构的的接口
// export const CREATE_ROLE_SELECT_ORG_NEEDED_API = '/system_module/org/findOrgListByUser'
//
// //创建角色:根据组织ID获取对应的菜单组
// export const GET_MENU_LIST_BY_ORG_ID = '/system_module/menu/findMenuListbyOrgId'
//
// //创建角色
// export const CREATE_ROLE_API = '/system_module/role/addRole'
//
// //获取角色列表
// export const GET_ROLE_LIST_API = '/system_module/role/findRoleList'
//
// //创建机构：调用拥有的上级组织的接口
// export const SELECT_SUPERIOR_ORG_WHEN_CREATE_ORG_API = '/system_module/role/findRoleListByOrgId'
//
// //创建机构
// export const CREATE_ORG__API = '/system_module/org/addOrg'
//
// //机构管理查询机构列表
// export const CHECK_ORG_LIST_API = '/system_module/org/findOrgList'
//
// //查看机构详情页面接口
// export const SHOW_ORG_DETAIL_API = '/system_module/org/findOrgDetail'
//
// //删除机构接口
// export const DEL_ORG_API = '/system_module/org/deleteOrgs'
//
// //编辑/修改机构接口
// export const MODIFY_ORG_API = '/system_module/org/updateOrg'
//
// //获取用户列表
// export const GET_USER_LIST_API = '/system_module/user/findUserList'
//
// //创建用户/新增用户
// export const CREATE_USER__API = '/system_module/user/addUser'
//
// //查看用户详情
// export const SHOW_USER_DETAIL_API = '/system_module/user/findUserById'
//
// //修改用户接口
// export const MODIFY_USER_API = '/system_module/user/updateUser'
//
// //删除用户接口
// export const DEL_USER_API = '/system_module/user/deleteUsers'
//
// //创建用户或机构时下拉选择组织机构时联动的角色
// export const GET_ROLE_LIST_WHEN_CREATA_API = '/system_module/role/findRoleListByOrgId'
//
// // 获取菜单管理菜单列表页
// export const GET_MENU_LIST_API = '/system_module/menu/findMenuList'
//
// // 编辑菜单
// export const MODIFY_MENU_API = '/system_module/menu/updateMenu'
//
// //查看菜单详情
// export const SHOW_MENU_DETAIL_API = '/system_module/menu/findMenuById'
//
// //查看角色详情
// export const SHOW_ROLE_DETAIL_API = '/system_module/role/findRoleById'
//
// //创建触发器时调用告警联系人列表接口
// export const GET_CONTACT_LIST_API = '/system_module/user/findUserListByToken'
//
// //查询机构拥有应用授权App列表
// export const GET_ORG_APP_LIST_API = '/system_module/app/findOrgAppList'
//
// //查询查询app列表
// export const GET_APP_LIST_API = '/system_module/app/findApps'
//
// //添加参数
// export const CREATE_PARAMS_API = 'system_module/app/insertSystemParam'
//
// //添加参数
// export const CHECK_PARAMS_LIST_API = 'system_module/app/findSystemParams'
//
// //查看参数详情页
// export const SHOW_PARAMS_DETAIL__API= 'system_module/app/showSystemParamDetail'
//
// //修改参数
// export const MODIFY_PARAMS_API= '/system_module/app/updateSystemParam'
//
// //删除参数
// export const DEL_PARAMS_API= '/system_module/app/deleteSystemParam'
//
// //查看设备控制记录列表
// export const CHECK_DEVICES_CONTROL_RECORD_LIST_API= '/quartz_module/task/findDevLog'
//
// //基本信息修改接口
// export const MODIFY_BASIC_INFO_API= '/system_module/org/updateOrgWechat'
//
//
