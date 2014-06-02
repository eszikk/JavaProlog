
move(loc(omaha), loc(chicago)).
move(loc(omaha), loc(denver)).
move(loc(chicago), loc(denver)).
move(loc(chicago), loc(los_angeles)).
move(loc(chicago), loc(omaha)).
move(loc(denver), loc(los_angeles)).
move(loc(denver), loc(omaha)).
move(loc(los_angeles), loc(chicago)).
move(loc(los_angeles), loc(denver)).

bfs(State, Goal, Path) :-
    bfs_help([[State]], Goal, RevPath), reverse(RevPath, Path).

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



