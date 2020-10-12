export default{
    api:process.env.NODE_ENV === 'development' ? 'http://127.0.0.1:80' : 'http://127.0.0.1:8010',
}