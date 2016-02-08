### Year 1 Software Workshop Game Tic-Tac-Toe implementation

Instructions on how to compile and run files from command line:	

	1. Running the server:
		java Server 
	2. Running the clients:
		java Client <username> <hostname>
		(for example: java Client John localhost)

Everything else is done in the GUI after we run the client.

Things which are working and implemented:

1. Client GUI (all the information about the clients(name, wins, losses, draws and availability)), and buttons:

2. Request button to request player to play.
3. Refresh button to refresh the client GUI.
4. Exit button to close the client.
5. The game GUI(updated after each move, and disabled until it is a certain players move)
  and at this point Client GUI is disabled and no other actions can be made (done
  in order to avoid exceptions)
6. If the player press request button when nothing is selected, 
  a GUI error window with OK button is displayed which shows the message
7. If the player press request button when he selected himself,
  a GUI error window with OK button is displayed which whows the certain message.
8. When player requests other player successfuly, opponent gets the request GUI window,
  with the selection of 'Yes" and 'No' buttons. If Yes button is pressed, game GUIs
  for both players are opened, if No button is pressed, the player which inivted
  to play gets a GUI error message which says that opponent refused to play.
9. If the player requests to play another player which is already requested to play,
  gets a GUI error message that other player is already requested to play.
10. If the player requests to play another player which is already in a game,
  gets a GUI error message that other player is already playing.
11. When game ends, there is a 3 seconds pause, and then game GUI board closes, and
   also Congratulation, Losser or Draw message is displayed for each player
   accordingly to score.
12. A infinite number of games at the same time is possible.
13. An ambiguity with the player with the same name is solved. (player gets error message
   that such name alrady exists, so he should try another nickname).