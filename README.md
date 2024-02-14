# FISHOP
## Requirement
1. PostgreSQL
2. SMTP
3. Stripe [api keys](https://dashboard.stripe.com/test/apikeys)

## How to setup
1. Clone repo:
```git clone https://github.com/DrViruss/Fishop.git```
3. Put all credentials to application properties:
```
spring.datasource.url=jdbc:postgresql://{postgres_ip}:{postgres_port}/{postgers_dbname}
spring.datasource.username={postgres_username}
spring.datasource.password={postgres_password}
stripe.api.publicKey={stripe_publickey}
stripe.api.secretKey={stripe_secretkey}
spring.mail.host=smtp.freesmtpservers.com
spring.mail.port=25
spring.mail.properties.mail.smtp.auth=false
```
3. Run
```./gradle bootRun```

## How to test email (in case of example smtp server using)
1. Go to [link](https://www.wpoven.com/tools/free-smtp-server-for-testing)
2. input your email
3. profit
