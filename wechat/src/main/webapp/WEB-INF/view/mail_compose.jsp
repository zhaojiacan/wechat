<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>


<!-- Mirrored from www.zi-han.net/theme/hplus/mail_compose.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:19:10 GMT -->
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>H+ 后台主题UI框架 - 写信</title>
<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
<meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

<link href="css/style.css" rel="stylesheet">
<link rel="shortcut icon" href="favicon.ico">
<link href="css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="css/plugins/summernote/summernote.css" rel="stylesheet">
<link href="css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">
<link href="css/style.min862f.css?v=4.1.0" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="plugins/bootstrap-fileupload/css/fileinput.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-content mailbox-content">
						<div class="file-manager">
							<a class="btn btn-block btn-primary compose-mail" href="mail_compose.html">写信</a>
							<div class="space-25"></div>
							<h5>文件夹</h5>
							<ul class="folder-list m-b-md" style="padding: 0">
								<li><a href="mailbox.html"> <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning pull-right">16</span>
								</a></li>
								<li><a href="mailbox.html"> <i class="fa fa-envelope-o"></i> 发信
								</a></li>
								<li><a href="mailbox.html"> <i class="fa fa-certificate"></i> 重要
								</a></li>
								<li><a href="mailbox.html"> <i class="fa fa-file-text-o"></i> 草稿 <span class="label label-danger pull-right">2</span>
								</a></li>
								<li><a href="mailbox.html"> <i class="fa fa-trash-o"></i> 垃圾箱
								</a></li>
							</ul>
							<h5>分类</h5>
							<ul class="category-list" style="padding: 0">
								<li><a href="mail_compose.html#"> <i class="fa fa-circle text-navy"></i> 工作
								</a></li>
								<li><a href="mail_compose.html#"> <i class="fa fa-circle text-danger"></i> 文档
								</a></li>
								<li><a href="mail_compose.html#"> <i class="fa fa-circle text-primary"></i> 社交
								</a></li>
								<li><a href="mail_compose.html#"> <i class="fa fa-circle text-info"></i> 广告
								</a></li>
								<li><a href="mail_compose.html#"> <i class="fa fa-circle text-warning"></i> 客户端
								</a></li>
							</ul>

							<h5 class="tag-title">标签</h5>
							<ul class="tag-list" style="padding: 0">
								<li><a href="mail_compose.html"><i class="fa fa-tag"></i> 朋友</a></li>
								<li><a href="mail_compose.html"><i class="fa fa-tag"></i> 工作</a></li>
								<li><a href="mail_compose.html"><i class="fa fa-tag"></i> 家庭</a></li>
								<li><a href="mail_compose.html"><i class="fa fa-tag"></i> 孩子</a></li>
								<li><a href="mail_compose.html"><i class="fa fa-tag"></i> 假期</a></li>
								<li><a href="mail_compose.html"><i class="fa fa-tag"></i> 音乐</a></li>
								<li><a href="mail_compose.html"><i class="fa fa-tag"></i> 照片</a></li>
								<li><a href="mail_compose.html"><i class="fa fa-tag"></i> 电影</a></li>
							</ul>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-9 animated fadeInRight">
				<div class="mail-box-header">
					<div class="pull-right tooltip-demo">
						<a href="mailbox.html" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="存为草稿"><i class="fa fa-pencil"></i> 存为草稿</a> <a href="mailbox.html" class="btn btn-danger btn-sm" data-toggle="tooltip" data-placement="top" title="放弃"><i class="fa fa-times"></i> 放弃</a>
					</div>
					<h2>写信</h2>
				</div>
				<div class="mail-box">

					<form class="form-horizontal" id="mail-form" method="post" action="mail/sendMail.do">
						<div class="mail-body">


							<div class="form-group">
								<label class="col-sm-2 control-label">发送到：</label>

								<div class="col-sm-10">
									<input type="text" class="form-control" name="toAddress">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">主题：</label>

								<div class="col-sm-10">
									<input type="text" class="form-control" name="subject">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">上传附件：</label>
								<div class="col-sm-10">
									<input id="file-1" type="file" multiple class="file" data-overwrite-initial="false" data-min-file-count="2">
								</div>
							</div>




							<div class="mail-text h-200" style="padding-top:10px;">
								<div class="mail-context">
									<div class="form-group">
										<label class="col-sm-2 control-label">内容：</label>
										<div class="col-sm-10">
											<!-- 加载编辑器的容器 -->
											<script id="ueditor" name="content" type="text/plain">
                                                                                                                                            
   							                </script>
											<!-- 配置文件 -->
											<script type="text/javascript" src="ueditor-min-1.4.3/ueditor.config.js"></script>
											<!-- 编辑器源码文件 -->
											<script type="text/javascript" src="${base}/ueditor-min-1.4.3/ueditor.all.js"></script>
											<script type="text/javascript" src="${base}/ueditor-min-1.4.3/lang/zh-cn/zh-cn.js"></script>
											<script type="text/javascript" src="${base}/ueditor-min-1.4.3/ueditor.parse.min.js"></script>
											<!-- 实例化编辑器 -->
											<script type="text/javascript">
													    var ue = UE.getEditor('ueditor', {
													        autoHeightEnabled: true,
													        autoFloatEnabled: true,
													        initialFrameWidth:'100%',
													        initialFrameHeight:320,
													        maximumWords:1000,
													        autoHeightEnabled:true
													       
													    });
										    </script>
										</div>
									</div>

								</div>

								<div class="clearfix"></div>
							</div>
						</div>
						<div class="mail-body text-right tooltip-demo">
							<a href="javascript:;" onclick="sendMail();" class="btn btn-sm btn-primary" data-toggle="tooltip" data-placement="top" title="Send"><i class="fa fa-reply"></i> 发送</a> <a href="mailbox.html" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="Discard email"><i class="fa fa-times"></i> 放弃</a> <a href="mailbox.html" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="Move to draft folder"><i class="fa fa-pencil"></i> 存为草稿</a>
						</div>
						<div class="clearfix"></div>
					</form>
				</div>

			</div>
		</div>
	</div>
	<script src="js/jquery.min.js?v=2.1.4"></script>
	<script src="js/bootstrap.min.js?v=3.3.6"></script>
	<script src="js/content.min.js?v=1.0.0"></script>
	<script src="js/plugins/iCheck/icheck.min.js"></script>
	<script src="js/plugins/summernote/summernote.min.js"></script>
	<script src="js/plugins/summernote/summernote-zh-CN.js"></script>
	<script src="plugins/bootstrap-fileupload/js/fileinput.min.js"></script>
	<script src="plugins/bootstrap-fileupload/js/fileinput_locale_zh.js"></script>
	<script>

	    $("#file-1").fileinput({
	        uploadUrl: '#', // you must set a valid URL here else you will get an error
	        overwriteInitial: false,
	        maxFileSize: 1000,
	        maxFileCount:2,
	        showUpload: true,
	        enctype: 'multipart/form-data',
	        slugCallback: function(filename) {
	            return filename.replace('(', '_').replace(']', '_');
	        }
		});
    
        $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",});$(".summernote").summernote({lang:"zh-CN"})});var edit=function(){$(".click2edit").summernote({focus:true})};var save=function(){var aHTML=$(".click2edit").code();$(".click2edit").destroy()};
    	function sendMail(){
    		$("#mail-form").submit();
    	}
    </script>
	<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>


<!-- Mirrored from www.zi-han.net/theme/hplus/mail_compose.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:19:15 GMT -->
</html>
