
    <style>
 		.e-container{min-width: 1017px; padding:0px; margin:0px auto; border: 0px solid white;}
        .e-board{width: 100%; border-collapse: collapse; border:1px solid #ddd;}
        .e-board td{width: 100%;font-family: 'microsoft yahei'; font-size: 14px;}
        .e-content, .e-footer{padding: 20px; }
        .e-logo{  height: 64px;}
      	.e-logo img{width: 100%;height:100%}
        .e-content img{max-width: 968px;}
		.e-content{min-height: 100px; }
 		.e-footer{}
    </style>
</style>
<body>
    <div class="e-container">
    	<table class="e-board">
    		<tr style="height: 64px;">
    			<td class="e-logo" style="padding:0px; height:64px;">
    				<img src='convoy_log.gif'/>
    			</td>
    		</tr>
    		<tr>
    			<td class="e-content">
    				${body!}
    			</td>
    		</tr>
    		<tr>
    			<td class="e-footer">
    				${sign!}
    			</td>
    		</tr>
    	</table>
 
    </div>
    
</body>
