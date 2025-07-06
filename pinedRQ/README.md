# MDD 2022 Summer School: Data Security and Privacy for Outsourced Data in the Cloud (Lab Session)
## Get the code
```bash
git clone https://gitlab.inria.fr/tallard/mdd2022-public.git
```

## Run the docker image and open Jupyter Lab through your browser
Open a shell and go to the directory in which you cloned the code. 

If your OS is a Linux distribution, run the following: 

```bash
docker run -ti --network host -e NB_UID=$UID -v $(pwd):/home/jovyan/work registry.gitlab.inria.fr/tallard/mdd2022-public:latest
```

If your OS is a Mac or a Windows, run the following: 

```bash
docker run -ti -p 8888:8888 -e NB_UID=$UID -v $(pwd):/home/jovyan/work registry.gitlab.inria.fr/tallard/mdd2022-public:latest
```

Copy-paste in the address bar of your web browser the full URL displayed in your shell. 

## Use your favorite IDE
Open RunMe.ipynb and pinedrq.py in your favorite IDE. 

## Perform the assignment 
Open the assignment in PrivDB_Assignment-MDD_22.pdf. You are now ready to go!
