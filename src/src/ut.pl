
move(loc(omaha), loc(chicago),1).
move(loc(omaha), loc(denver),2).
move(loc(chicago), loc(denver),3).
move(loc(chicago), loc(los_angeles),4).
move(loc(chicago), loc(omaha),1).
move(loc(denver), loc(los_angeles),2).
move(loc(denver), loc(omaha),3).
move(loc(los_angeles), loc(chicago),4).
move(loc(los_angeles), loc(denver),1).

move(A,B) :-
   move(A,B,_).

bfs(A,B,Path,D) :-
   bfs(A,B,Path),
   path_distance(Path,D).

path_distance([_], 0).
path_distance([A,B|Cs], S1) :-
   move(A,B,D),
   path_distance([B|Cs],S2),
   S1 is S2+D.

bfs(A,B, Path) :-
    bfs_help([[A]], B, RevPath), reverse(RevPath, Path).

bfs_help([[Goal|Path]|_], Goal, [Goal|Path]).
bfs_help([Path|RestPaths], Goal, SolnPath) :-
    extend(Path, NewPaths),
    append(RestPaths, NewPaths, TotalPaths),
    bfs_help(TotalPaths, Goal, SolnPath).

extend([State|Path], NewPaths) :-
    bagof([NextState,State|Path],
          (move(State, NextState), not(member(NextState, [State|Path]))),
          NewPaths), !.
extend(_, []).




