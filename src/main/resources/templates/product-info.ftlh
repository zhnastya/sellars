          <!DOCTYPE html>
          <html>
          <head>
              <title>SELLARS</title>
          </head>
          <body>
          <h1>SELLARS</h1>
          <hr>
              <h4> Подробная информация о товаре </h4>
              <b>Название товара: </b>${product.title}<br>
              <b>Описание товара: </b>${product.description}<br>
              <b>Цена товара: </b>${product.price}<br>
              <b>Город товара: </b>${product.city}<br>
              <b>Владелец товара: </b>${product.author}<br>

          <hr>
          <h3>
          </h3>
          <form action="/product/delete/${product.id}" method="post">
                    <input type= "submit" value="Удалить товар"/>
          </form>
          </body>
          </html>