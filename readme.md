# hilow-card-game
My take on test assignment. Working but far from an ideal solution (missing server some important server validations and inefficient way of using data structures).

Server:
1. Run Server.java
2. Follow the instructions

Client:
1. Run Client.java
2. Follow the instructions

Gameplay:
Hi-Lo card game.

Each round player must guess if next card drawn from the deck is equal, higher or lower to the base card.

Card ranks are from 2 to 10. Jack, Queen, King and Ace have rank 10. 

To set a base card, type SET BASE when client displays "Press enter to start a round...". You can then choose a base card*. 

*example how to assign Ace of Spades: SA

*example how to assign Seven of Diamonds: D7

*case sensitive, e.g. h3 does not work


---

Original assignment:
Assignment

Introduction: You are required to implement a Hi-Lo card game. Game Card deck is standard 52 cards https://en.wikipedia.org/wiki/Standard_52-card_deck. Each round player must guess if next card drawn from the deck is equal, higher or lower to the base card. Card ranks are from 2 to 10. Jack, Queen, King and Ace have rank 10. When current deck gets empty, new deck is initialized.

Gameplay requirements: 1. When round is started server sends to player StartRoundRequest. Client must display base card from StartRoundRequest. 2. Client has 10 seconds to make an action. During this time player has a choice of three actions - 'higher', 'lower' or 'equal'.
3. Client must display countdown values to player. 4. When countdown reaches zero client stops accepting actions 5. If player does not make an action, go to point 7 6. Server checks internally player action and compares base card rank to result card rank. - If player action was correct, player wins - If player action was incorrect, player loses 7. Server sends FinishRoundRequest to client 8. Client must display win result to player 9. Previous result card became new 'base card' 10. Repeat steps 1 to 10.

General implementation requirements: Server must support multiple clients at the same time 1. Console interface should be used to control both server and client. 2. Separate client and server applications. 3. Socket connection between server and client. 4. Validation of console commands. 5. Textual or binary protocol between client and server. 6. Sufficient unit test coverage 7. Provided project skeleton is only to get started. Existing objects can be enhanced as necessary. 8. Minimal use of external frameworks 9. Code must compile, be easily readable and follow 'Clean Code' principles 10. Do not over complicate. Simplest solution is most likely the correct one.

Server implementation requirements: 1. Server must control game flow 2. Server must have necessary validation 3. Server must implement GameService interface

Client implementation requirements: 1. Client displays all messages sent by the server 
2. Client must implement GameClient interface

Deliverables: 1. Downloadable solution with sources and runnable jar uploaded to Google Drive or Dropbox 2. Instruction how to run both client and server

Restrictions: 1. Solution cannot be publicly uploaded 2. Assignment is personal and therefore must be fully implemented by the applicant
