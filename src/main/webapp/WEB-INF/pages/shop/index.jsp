<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="main-content">
    <div class="clearfix margin-bottom-10">
        <div class="categorys">
            <div class="level-nav">
                <a>全部分类</a>
            </div>
            <div class="level-content" id="indexCategoryDiv"></div>
        </div>
        <div class="banner pull-right">
            <div>
                <img src="${ctx}/resources/content/images/shop/banner1.jpg"/>
            </div>
            <div>
                <img src="${ctx}/resources/content/images/shop/banner2.jpg"/>
            </div>
            <div>
                <img src="${ctx}/resources/content/images/shop/banner3.jpg"/>
            </div>
            <div>
                <img src="${ctx}/resources/content/images/shop/banner4.jpg"/>
            </div>
            <div>
                <a onclick="companyView(this)" selfId="8aae242651aff14f0151cc5a484105ab">
                    <img src="${ctx}/resources/content/images/shop/banner5.jpg"/>
                </a>
            </div>
            <div>
                <a onclick="companyView(this)" selfId="8aae242651aff14f0151c25e038401d8">
                    <img src="${ctx}/resources/content/images/shop/banner6.jpg"/>
                </a>
            </div>
        </div>
    </div>

    <div class="adver margin-bottom-10">
        <a onclick="companyView(this)" selfId="ff808081518c25e4015190270851016a"><img src="${ctx}/resources/content/images/shop/ad1.jpg"
                /></a>
        <a onclick="companyView(this)" selfId="8aae242651aff14f0151c3539f770403"><img src="${ctx}/resources/content/images/shop/ad2.jpg"
                /></a>F
        <a onclick="companyView(this)" selfId="ff808081518c25e40151a3e45fac03a7"><img src="${ctx}/resources/content/images/shop/ad3.jpg"
                /></a>
    </div>
    <div class="article floor1" id="">
        <h2 class="row">
            <span>F1</span>室内装饰
            <a selfId="ff80808151565b2e015160b47e150009" firstId="ff80808151565b2e015160b47e150009" secondId="" thirdId=""
               class="more">更多</a>
        </h2>

        <div class="article-content row">
            <div class="article-lt">
                <div id="equipment1"></div>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e4015190270851016a"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f1/logo/lg1.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e4015194289fe701f4"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f1/logo/lg2.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151b2dbcc6b0059"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f1/logo/lg3.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151c25e038401d8"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f1/logo/lg4.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e40151a8f7d20d04c9"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f1/logo/lg5.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e40151903c029b01c0"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f1/logo/lg6.png"
                        /></a></span>
            </div>
            <div class="article-md row">
                <a onclick="productView(this)" selfId="8aae242651aff14f0151d7b873eb0e9e" cateId="ff808081516abc8201516b052121063a" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f1/pr/pr1.png"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151ecea9d470622" cateId="ff808081516abc8201516b009aa10599" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f1/pr/pr2.png"/></a>
                <a onclick="productView(this)" selfId="8aae242651aff14f0151d27dc2100903" cateId="ff808081516abc8201516b49d86a0efd"  class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f1/pr/pr3.png"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151ecc316d205eb" cateId="ff808081516abc8201516b2a8836099d" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f1/pr/pr4.png"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151dcdfffe900cb" cateId="ff808081516abc8201516b4e864a0f7e" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f1/pr/pr5.png"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151ed5df9af0838" cateId="ff808081516abc8201516b21c7380958" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f1/pr/pr6.png"/></a>
            </div>
            <div class="article-rt">
                <a onclick="productView(this)" selfId="4028808451d8c2550151ec7c0bbf05c7"  cateId="ff808081516abc8201516b1a4b5908ca" >
                    <img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                         data-original="${ctx}/resources/content/images/shop/home/f1/rt.jpg"/>
                </a>
            </div>
        </div>
    </div>
    <div class="article floor2" id="">
        <h2 class="row">
            <span>F2</span>室外建材
            <a selfId="ff80808151565b2e015160cf6af2003c" firstId="ff80808151565b2e015160cf6af2003c" secondId="" thirdId=""
               class="more">更多</a>
        </h2>

        <div class="article-content row">
            <div class="article-lt">
                <div id="equipment2"></div>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e401519044b69b01e0"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f2/logo/lg1.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e40151a3e45fac03a7"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f2/logo/lg2.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151c3539f770403"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f2/logo/lg3.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151c32121d4032c"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f2/logo/lg4.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e401518fafe622006f"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f2/logo/lg5.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e40151a47c4ae5043f"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f2/logo/lg6.png"
                        /></a></span>
            </div>
            <div class="article-md row">
                <a onclick="productView(this)" selfId="8aae242651aff14f0151cdb1ab8d06b6" cateId="ff808081516abc8201516b052121063a" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f2/pr/pr1.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151e7a30fa504dd" cateId="ff808081516abc8201516b3e44ab0c0f" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f2/pr/pr2.jpg"/></a>
                <a onclick="productView(this)" selfId="8aae242651aff14f0151d6a881b20bc8"  cateId="ff808081516abc8201516b3d4e230be2" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f2/pr/pr3.jpg"/></a>
                <a onclick="productView(this)" selfId="8aae242651aff14f0151d29186160948"   cateId="ff808081516abc8201516b49d86a0efd" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f2/pr/pr4.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151dcdae9a800b6" cateId="ff808081516abc8201516b4cb6b40f5b"  class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f2/pr/pr5.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151dc08bc1c0006"   cateId="ff808081516abc8201516b1beafe08df" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f2/pr/pr6.jpg"/></a>
            </div>
            <div class="article-rt">
                <a onclick="productView(this)" selfId="8aae242651aff14f0151d2080386087d" cateId="ff808081516abc8201516b2c4a3e09b3" >
                    <img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                         data-original="${ctx}/resources/content/images/shop/home/f2/rt.jpg"/>
                </a>
            </div>
        </div>
    </div>
    <div class="article floor3" id="">
        <h2 class="row">
            <span>F3</span>基础建设
            <a selfId="ff80808151565b2e015160be71d00018" firstId="ff80808151565b2e015160be71d00018" secondId="" thirdId=""
               class="more">更多</a>
        </h2>

        <div class="article-content row">
            <div class="article-lt">
                <div id="equipment3"></div>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151c291c42f028b"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f3/logo/lg1.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e401518ff5f236011c"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f3/logo/lg2.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151c3941a7d057d"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f3/logo/lg3.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151d16fec3b0727"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f3/logo/lg4.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151c2076ad60092"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f3/logo/lg5.png"
                        /></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e40151a398e85802c1"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f3/logo/lg6.png"
                        /></a></span>
            </div>
            <div class="article-md row">
                <a onclick="productView(this)" selfId="8aae242651aff14f0151cc8c927805b8" cateId="ff808081516abc8201516b1beafe08df" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f3/pr/pr1.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151ecd7179e0608" cateId="ff808081516abc8201516afd73ad0519" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f3/pr/pr2.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151f0e678e408c4" cateId="ff808081516abc8201516afd73ad0519" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f3/pr/pr3.jpg"/></a>
                <a onclick="productView(this)" selfId="8aae242651aff14f0151d7c837a40eec" cateId="ff808081516abc8201516afd73ad0519" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f3/pr/pr4.jpg"/></a>
                <a onclick="productView(this)" selfId="8aae242651aff14f0151d8212f3b136f" cateId="ff808081516abc8201516afec755052e" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f3/pr/pr5.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451f8dae501520a4737b5000e"  cateId="ff808081516abc8201516b51bf690fdc" class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f3/pr/pr6.jpg"/></a>
            </div>
            <div class="article-rt">
                <a onclick="productView(this)" selfId="4028808451d8c2550151e7969c8f045d" cateId="ff808081516abc8201516b3675520ad8" >
                    <img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                         data-original="${ctx}/resources/content/images/shop/home/f3/rt.jpg"/>
                </a>
            </div>
        </div>
    </div>
    <div class="article floor4" id="">
        <h2 class="row">
            <span>F4</span>机电设备
            <a selfId="ff80808151565b2e015160b27faf0001" firstId="ff80808151565b2e015160b27faf0001" secondId="" thirdId=""
               class="more">更多</a>
        </h2>

        <div class="article-content row">
            <div class="article-lt">
                <div id="equipment4"></div>
                <span class="img"><a onclick="companyView(this)" selfId="4028808451d8c2550151ed5d445f082c"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f4/logo/lg1.png"/></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151cc5a484105ab"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f4/logo/lg2.png"/></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="ff808081518c25e401518fd2550c00c9"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f4/logo/lg3.png"/></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151cc5c081f05ad"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f4/logo/lg4.png"/></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151c248e87f0147"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f4/logo/lg5.png"/></a></span>
                <span class="img"><a onclick="companyView(this)" selfId="8aae242651aff14f0151c362c6f0046e"><img class="lazy"
                                                                                                                src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                                data-original="${ctx}/resources/content/images/shop/home/f4/logo/lg6.png"/></a></span>
            </div>
            <div class="article-md row">
                <a onclick="productView(this)" selfId="4028808451d8c2550151ed4b5f4907d5" cateId="ff808081516abc8201516b5690e1106f"  class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f4/pr/pr1.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151ed31afe406d4" cateId="ff808081516abc8201516b5690e1106f"  class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f4/pr/pr2.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151ed38a1750740" cateId="ff808081516abc8201516b5690e1106f"  class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f4/pr/pr3.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151ed3ca37c0782" cateId="ff808081516abc8201516b5690e1106f"  class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f4/pr/pr4.png"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151ed3c160d0762" cateId="ff808081516abc8201516b44e31b0d47"  class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f4/pr/pr5.jpg"/></a>
                <a onclick="productView(this)" selfId="4028808451d8c2550151ecf2192c0639" cateId="ff808081516abc8201516b44e31b0d47"  class="item"><img class="lazy"
                                                                                                           src="${ctx}/resources/content/images/shop/lazy.png"
                                                                                                           data-original="${ctx}/resources/content/images/shop/home/f4/pr/pr6.jpg"/></a>
            </div>
            <div class="article-rt">
                <a onclick="productView(this)" selfId="4028808451d8c2550151ed3487bd06fd"  cateId="ff808081516abc8201516b44e31b0d47">
                    <img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                         data-original="${ctx}/resources/content/images/shop/home/f4/rt.jpg"/>
                </a>
            </div>
        </div>
    </div>

    <div class="partners">
        <h2>签约公司</h2>

        <div class="partners-logo">
            <a onclick="companyView(this)" selfId="8aae242651aff14f0151d16fec3b0727">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg01.png" width="198" height="82"/></i>
            </a>
            <a onclick="companyView(this)" selfId="8aae242651aff14f0151c3941a7d057d">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg02.png" width="198" height="82"/></i>
            </a>
            <a onclick="companyView(this)" selfId="8aae242651aff14f0151cc5c081f05ad">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg03.png" width="198" height="82"/></i>
            </a>
            <a onclick="companyView(this)" selfId="ff808081518c25e4015190270851016a">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg04.png" width="198" height="82"/></i>
            </a>
            <a onclick="companyView(this)" selfId="8aae242651aff14f0151cc5a484105ab">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg05.png" width="198" height="82"/></i>
            </a>
            <a onclick="companyView(this)" selfId="8aae242651aff14f0151c25e038401d8">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg06.png" width="198" height="82"/></i>
            </a>
            <a onclick="companyView(this)" selfId="ff808081518c25e401519012f95a0153">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg07.png" width="198" height="82"/></i>
            </a>
            <a onclick="companyView(this)" selfId="8aae242651aff14f0151c3539f770403">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg08.png" width="198" height="82"/></i>
            </a>
            <a onclick="companyView(this)" selfId="ff808081518c25e40151a8f7d20d04c9">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg09.png" width="198" height="82"/></i>
            </a>
            <a onclick="companyView(this)" selfId="ff808081518c25e40151a3c6c25e037d">
                <i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                        data-original="${ctx}/resources/content/images/shop/home/cm_lg/cm_lg10.png" width="198" height="82"/></i>
            </a>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/shop/indexCategory.jsp"%>
<%@ include file="/WEB-INF/pages/shop/secondCategoryTemplate.jsp"%>
<script src="${ctx}/resources/script/plugins/slick/slick.min.js"></script>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/models/shop/indexCategory.js"></script>
<script src="${ctx}/resources/script/models/shop/index.js"></script>
<script src="${ctx}/resources/script/models/shop/CommonShop.js"></script>
<script>
    $(function () {
        $("img.lazy").lazyload({
            effect: "fadeIn",
            threshold: 200
        });
    });
</script>










