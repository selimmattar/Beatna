var crypto=require('crypto');
var uuid=require('uuid');
var express= require("express");
var BodyParser=require('body-parser');
var mysql=require("mysql");
//connect to mysql
var con=mysql.createConnection({
  host:'localhost',
  user:'root',
  password:'',
  database:'beatna'
});
var app= express();
app.use(BodyParser.json());
app.use(BodyParser.urlencoded({extended:true}));
var getRandomString=function(length){
  return crypto.randomBytes(Math.ceil(length/2))
  .toString('hex');
};
var sha512=function(password,salt){
  var hash=crypto.createHmac('sha512',salt);
  hash.update(password);
  var value=hash.digest('hex');
  return {
    salt:salt,
    passwordHash:value
  }
};
function checkHashPassword(userPassword,salt){
  var passwordData=sha512(userPassword,salt);
  return passwordData;
}
function saltHashPassword(userPassword) {
  var salt= getRandomString(16);
  var passwordData=sha512(userPassword,salt);
  return passwordData;
}
app.post('/register/',(req,res,next)=>
{
  var post_data=req.body;
  var uid=uuid.v4();
  var plaint_password= post_data.password;
  var hash_data=saltHashPassword(plaint_password);
  var password=hash_data.passwordHash;
  var salt=hash_data.salt;
  var name=post_data.name;
  var email=post_data.email;
   console.log(email);
    console.log(uid);

      
  con.query('SELECT * FROM user where email=?',[email],function(err,result,fields){
    con.on('error',function(err){
      console.log('[MySQL ERROR]',err);
    });
    if(result && result.length)
      res.json("User already exists");
    else con.query('INSERT INTO user( unique_id, name, email, encrypted_password, salt,updated_at, created_at) VALUES (?,?,?,?,?,NOW(),NOW())',[uid,name,email,password,salt],function(err,result,fields){
    con.on('error',function(err){
      console.log('[MySQL ERROR]',err);
      res.json('Register error',err);
    });
    console.log(err);
    res.json("Register successful");
    });
  });

 
});

app.post('/login/',(req,res,next)=>{
  var post_data=req.body;
  var user_password=post_data.password;
  var email=post_data.email;
   con.query('SELECT * FROM user where email=?',[email],function(err,result,fields){
    con.on('error',function(err){
      console.log('[MySQL ERROR]',err);
    });
    if(result && result.length)
     {
      var salt=result[0].salt;
      var encrypted_password=result[0].encrypted_password;
      var hashed_password=checkHashPassword(user_password,salt).passwordHash
      if(encrypted_password==hashed_password)
        res.end(JSON.stringify(result[0]));
      else res.end("Wrong password");
     }
    else res.end("User non existant");
  });
});

app.post('/user_getByUid/',(req,res,next)=>{
  var post_data=req.body;
  var uid=post_data.uid;
   con.query('SELECT * FROM user where unique_id=?',[uid],function(err,result,fields){
    con.on('error',function(err){
      console.log('[MySQL ERROR]',err);
    });
    if(result && result.length)
     {
        res.end(JSON.stringify(result[0]));
      
     }
    else res.json("User non existant");
  });
});

app.post('/song_getById/',(req,res,next)=>{
  var post_data=req.body;
  var id=post_data.id;
   con.query('SELECT * FROM song where id=?',[id],function(err,result,fields){
    con.on('error',function(err){
      console.log('[MySQL ERROR]',err);
    });
    if(result && result.length)
     {
        res.end(JSON.stringify(result[0]));
      
     }
    else res.json("User non existant");
  });
});

app.post('/post_getAll/',(req,res,next)=>{
  var post_data=req.body;
  var user_password=post_data.password;
  var email=post_data.email;
   con.query('SELECT * FROM post ',function(err,result,fields){
    con.on('error',function(err){
      console.log('[MySQL ERROR]',err);
    });
    if(result && result.length)
     {
      console.log(JSON.stringify(result));
        res.end(JSON.stringify(result));
      
     }
    else res.json("No data found");
  });
})

app.get('/',(req,res,next)=>{
  console.log('Password:123456');
  var encrypt=saltHashPassword("123456");
  console.log('Encrypt: '+encrypt.passwordHash);
  console.log("Salt: "+encrypt.salt);
})

app.listen(3000,()=>{console.log("Listening..")});