if [ -f ~/.bashrc ]; then
  source ~/.bashrc
fi

export PATH="$HOME/compiler/cs143/bin:$HOME/.parts/autoparts/bin:$PATH"
eval "$(parts env)"
