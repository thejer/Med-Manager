#create the new directory will contain que October generated apk
  mkdir $ HOME /buildApk/ 
  mkdir $HOME/android/

  #copy generated apk from build folder to the folder just created
  cp -R app/build/outputs/apk/app-debug.apk $HOME/android/
  #go to home and git setup  
  cd $ HOME 
  git config --global user.email "mokomiab@gmail.com" 
  git config --global user.name "Jerry Adeleye" 
  # Clone the repository in the folder buildApk
  git clone --depth=10 --branch master = https://thejer:$GITHUB_API_KEY@github.com/thejer/Med-Manager master > /dev/null 
  #go into directory and copy data we're interested
  cp -Rf $HOME/android/* .
  #add, commit and push files
  git add -A
  git commit -m "Travis build $TRAVIS_BUILD_NUMBER pushed"
  git push -fq origin master > /dev/null
  echo "Done"
