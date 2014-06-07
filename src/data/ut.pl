move(A, B) :-
	move(A, B, _).

bfs(A, B, C, D) :-
	bfs(A, B, C),
	path_distance(C, D).

path_distance([_], 0).
path_distance([A, B|C], D) :-
	move(A, B, F),
	path_distance([B|C], E),
	D is E+F.

bfs(A, B, D) :-
	bfs_help([[A]], B, C),
	reverse(C, D).

bfs_help([[A|B]|_], A, [A|B]).
bfs_help([A|B], E, F) :-
	extend(A, C),
	append(B, C, D),
	bfs_help(D, E, F).

extend([A|B], D) :-
	bagof([C, A|B],
	      (move(A, C), not(member(C, [A|B]))),
	      D), !.
extend(_, []).

save(A) :-
	absolute_file_name(A, C),
	findall(B, source_file(B, C), D),
	tell(A),
	maplist(listing, D),
	told.



