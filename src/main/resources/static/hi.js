console.log('js 실행됨');

function Post(){
    console.log( 'doPost 실행' );
    // 1. HTML입력받은 값 가져오기
    let name = document.querySelector('#name').value;
    let content = document.querySelector('#content').value;
        console.log( name );
        console.log( content );
    // 2. 객체화(선택)
    let info = {
        bname : name ,
        bcontent : content
    };
    console.log( info );

       $.ajax({
            url : '/hi/post.do',
            method : 'post',
            data : info ,
            success : function( result ){
                console.log( result ); // 성공시 true / 실패면 false
                if( result == true ){

                    Get();
                }
            }
       }) // ajax end
} // method end

Get();
function Get(){
    $.ajax({
        url : '/hi/get.do' ,
        method : 'get' ,
        success : function result( resultValue ){
            console.log( resultValue );
            let tbody = document.querySelector('table tbody')

            let html = ``;
                for( let i = 0 ; i < resultValue.length ; i++ ){
                    html += `<tr>
                                 <th> ${ resultValue[i].bno } </th>
                                 <th> ${ resultValue[i].bname} </th>
                                 <th> ${ resultValue[i].bcontent}</th>
                             </tr>`
                } // for end
            // 3. 대입
            tbody.innerHTML = html;
        } // success end
    }) // ajax end
} // m end
