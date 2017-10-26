/**
 * Created by jws on 2017/5/11.
 */
function refresh(){
    $.ajax({
        type:'POST',
        url:getPath()+'/createAllIndex',
        success:function(data){
            if(data.result ==1){
                alert("生成索引成功！");
            }else {
                alert(data.msg) ;
            }
        }
    });
}
