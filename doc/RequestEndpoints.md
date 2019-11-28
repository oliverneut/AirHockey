```
POST /user/register?user=*email_address*&password=*password*&username=*username*

POST /user/login?user=*email_address*&password=*password*

POST /user/logout

GET /match/join?user=*email address*
    returns matchid, to be used in web-socket connection

WS /match/join/:matchid
```