# gitlab

https://gitee.com/mirrors/gitlab


https://www.cnblogs.com/sunsky303/p/11139074.html
https://www.cnblogs.com/sunsky303/p/10775126.html
https://www.cnblogs.com/sunsky303/p/10764096.html
https://blog.csdn.net/bbwangj/article/details/81169685


[gitlab使用教程](https://blog.csdn.net/Adelly/article/details/79099772#_Toc480656598)

https://blog.csdn.net/sinat_30603081/article/details/81353712
https://blog.csdn.net/bbwangj/article/details/81170311

# install gitlab-runner
https://docs.gitlab.com/runner/install/docker.html

```
docker run -d --name gitlab-runner --restart always \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v /srv/gitlab-runner/config:/etc/gitlab-runner \
  gitlab/gitlab-runner:latest
```