
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

save(A) :-
	absolute_file_name(A, C),
	findall(B, source_file(B, C), D),
	tell(A),
	maplist(listing, D),
	told.




