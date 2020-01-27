## Combination & Permutation



> 순열, 조합, 중복 순열, 중복 조합에 대해 알아보자
>
> [참고자료](https://yabmoons.tistory.com/99)



- 관련 문제

  - [N과 M 시리즈](https://www.acmicpc.net/workbook/view/2052)




#### 순열(Permutation) : 서로 다른 n개 중 r개를 뽑는 경우

- 개념

  - nPr = n * (n-1) * (n-2) * ...(n-r-1)

  - 순서가 있는 경우

    - {1, 2, 3}, {2, 1, 3}, {2, 3, 1}, {3, 1, 2}는 모두 다른 경우의 수로 간주한다.

    

- 소스코드 : [백준 N과 M(1)](https://www.acmicpc.net/problem/15649)

  ```c++
  void permutation(int length) {
  	if (length == m) {
  		for (int i = 0; i < m; i++) {
  			cout << arr[i]<<' ';			
  		}
  		cout << "\n";
  		return;
  	}
  	for (int i = 1; i <= n; i++) {
  		if (visit[i])
  			continue;
  		visit[i] = true;
  		arr[length] = i;
  		permutation(length + 1);
  		visit[i] = false;
  	}
  }
  
  int main() {
  	ios::sync_with_stdio(false);
  	cin.tie(0);	cout.tie();
  	freopen("input.txt", "r", stdin);
  	cin >> n >> m; // n개 중 m개를 중복없이 고름
  	permutation(0);
  }
  ```

- 입, 출력

  ```powershell
  #1
  input
  3 1
  output
  1
  2
  3
  
  #2
  input
  4 2
  output
  1 2
  1 3
  1 4
  2 1
  2 3
  2 4
  3 1
  3 2
  3 4
  4 1
  4 2
  4 3
  
  #3
  input
  4 4
  output
  1 2 3 4
  1 2 4 3
  1 3 2 4
  1 3 4 2
  1 4 2 3
  1 4 3 2
  2 1 3 4
  2 1 4 3
  2 3 1 4
  2 3 4 1
  2 4 1 3
  2 4 3 1
  3 1 2 4
  3 1 4 2
  3 2 1 4
  3 2 4 1
  3 4 1 2
  3 4 2 1
  4 1 2 3
  4 1 3 2
  4 2 1 3
  4 2 3 1
  4 3 1 2
  4 3 2 1
  ```

- 

#### 조합(Combination) : 서로 다른 n개 중 순서에 상관없이 r개를 뽑는 경우

- 개념

  - nCr = nPr / r! = n!/r!(n-r)!
  - 순서가 없는 경우
    - {1, 2, 3}, {2, 1, 3}, {2, 3, 1}, {3, 1, 2}는 모두 같은 경우의 수로 간주한다.

- 소스코드 : [백준 N과 M(2)](https://www.acmicpc.net/problem/15650)

  ```c++
  int n, m;
  bool visit[9];
  int arr[9];
  void combination(int length, int idx) { // 시작점, 몇개를 뽑는지
  	if (length == m) { // 원하는 만큼 뽑았음
  		for (int i = 0; i < m; i++) {
  				cout << arr[i] << ' ';
  		}
  		cout << "\n";
  		return;
  	}
  	for (int i = idx; i <= n; i++) { // 1부터 n까지 뽑음
  		if (visit[i]==true)
  			continue;
  		visit[i] = true;
  		arr[length] = i;
  		combination(length + 1, i+1);
  		visit[i] = false;
  	}
  }
  int main() {
  	ios::sync_with_stdio(false);
  	cin.tie(0);	cout.tie();
  	freopen("input.txt", "r", stdin);
  	cin >> n >> m;
  	combination(0,1);	
  }
  ```

- 입, 출력

  ```powershell
  #1
  input
  3 1
  output
  1
  2
  3
  
  #2
  input
  4 2
  output
  1 2
  1 3
  1 4
  2 3
  2 4
  3 4
  
  #3
  input
  4 4
  output
  1 2 3 4
  ```