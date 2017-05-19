$(function(){
	initTJCate();
});
function initTJCate(){
	//初始化推荐类别
	var tjcName = ['地板','门类','油漆涂料','家具','窗类'];
	var tjCId = ['ff80808151565b2e015160d02218004c','ff80808151565b2e015160d2a95a005b','ff80808151565b2e015160e459d300a2','ff80808151565b2e015160f85c5c014c','ff80808151565b2e015160d3ba330064'];
	var tjpCId = ['ff80808151565b2e015160b47e150009','ff80808151565b2e015160b47e150009','ff80808151565b2e015160b47e150009','ff80808151565b2e015160b47e150009','ff80808151565b2e015160b47e150009'];
	for(var i=1;i<6;i++){
		$('#tjsecondId'+i).html(tjcName[i-1]);
		$('#tjsecondId'+i).attr('selfId',tjCId[i-1]);
		$('#tjsecondId'+i).attr('firstId',tjpCId[i-1]);
		$('#tjsecondId'+i).attr('secondId',tjCId[i-1]);
	}
}