export const oktaConfig = {
    clientId : '0oaea********',
    issuer: 'https://dev-******.okta.com/oauth2/default',
    redirectUri : 'http://localhost:3000/login/callback',
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck : true,
}