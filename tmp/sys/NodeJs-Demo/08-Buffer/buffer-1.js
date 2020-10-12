const buf = Buffer.from('runoob', 'ascii');

// 输出 72756e6f6f62
console.log("16进制数据:",buf.toString('hex'));

// 输出 cnVub29i
console.log("Base64数据："+buf.toString('base64'));

console.log("binary数据："+buf.toString('utf8'));