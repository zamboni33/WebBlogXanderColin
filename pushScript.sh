#! /bin/sh


# script to push code easily

cp -r /home/xander/workspace/WebBlogXanderColin/src /home/xander/WebBlogXanderColin/ ;
echo "Copying src folder";

cp -r /home/xander/workspace/WebBlogXanderColin/war /home/xander/WebBlogXanderColin/ ;
echo "Copying war folder";

git add ./* ;

git commit -m $1;

read -p "Commit Message";

git push;

