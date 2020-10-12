from django.shortcuts import render
 
def hello(request):
    context          = {}
    context['hello'] = 'Hello World, znpp!'
    context['i'] = 2
    context['ath_list'] = {'a','b','c'}
    return render(request, 'hello.html', context)
