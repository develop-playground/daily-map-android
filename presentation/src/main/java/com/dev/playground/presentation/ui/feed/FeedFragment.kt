package com.dev.playground.presentation.ui.feed

import android.os.Bundle
import android.view.View
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.base.SimpleBindingAdapter
import com.dev.playground.presentation.databinding.FragmentFeedBinding
import com.dev.playground.presentation.ui.dialog.DropDialog
import com.dev.playground.presentation.ui.dialog.show
import com.dev.playground.presentation.ui.feed.FeedContract.Effect.RouteEditPage
import com.dev.playground.presentation.ui.feed.FeedContract.Effect.ShowRemoveDialog
import com.dev.playground.presentation.ui.feed.FeedContract.Event.OnClickDeleteMemory
import com.dev.playground.presentation.ui.feed.FeedContract.State.Success
import com.dev.playground.presentation.ui.modify.ModifyMemoryActivity
import com.dev.playground.presentation.ui.modify.ModifyMemoryActivity.Companion.KEY_MEMORY_BUNDLE
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.startActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed), ScrollableScreen {

    companion object {
        fun newInstance() = FeedFragment()

        val TAG: String = FeedFragment::class.java.simpleName
    }

    private val viewModel by viewModel<FeedViewModel>()
    private val feedAdapter by lazy {
        SimpleBindingAdapter(FeedViewHolder::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initCollects()
    }

    private fun initViews() = with(binding) {
        vm = viewModel
        srlFeed.setOnRefreshListener {
            viewModel.fetch()
            binding.srlFeed.isRefreshing = false
        }

        rvFeed.apply {
            itemAnimator = null
            adapter = feedAdapter
            addItemDecoration(FeedItemDecoration())
        }
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                uiState.collect { state ->
                    when (state) {
                        is Success -> feedAdapter.submitList(state.itemList) {
                            scrollTop()
                        }
                        else -> Unit
                    }
                }
            }
            launch {
                effect.collect {
                    when (it) {
                        is RouteEditPage -> activity?.startActivity<ModifyMemoryActivity> {
                            putExtra(KEY_MEMORY_BUNDLE, it.bundle)
                        }
                        is ShowRemoveDialog -> context?.let { c ->
                            DropDialog(c).show {
                                contentText = getString(R.string.feed_delete_memory_content)
                                leftText = getString(R.string.feed_delete_cancel)
                                rightText = getString(R.string.feed_delete_ok)
                                onRightClick = {
                                    setEvent(OnClickDeleteMemory(it.id))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun scrollTop() = binding.rvFeed.smoothScrollToPosition(0)

}