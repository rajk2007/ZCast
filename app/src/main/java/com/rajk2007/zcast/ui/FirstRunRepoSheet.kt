package com.rajk2007.zcast.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajk2007.zcast.R
import com.rajk2007.zcast.databinding.FirstRunRepoSheetBinding
import com.rajk2007.zcast.databinding.ItemRepoInstallerBinding
import com.rajk2007.zcast.utils.AppContextUtils.loadRepository
import com.rajk2007.zcast.utils.Coroutines.ioSafe
import com.rajk2007.zcast.utils.Coroutines.main

data class RepoItem(
    val name: String,
    val url: String,
    var isChecked: Boolean = true,
    var status: RepoStatus = RepoStatus.PENDING
)

enum class RepoStatus { PENDING, INSTALLING, SUCCESS, FAILED }

class FirstRunRepoSheet : BottomSheetDialogFragment() {
    private var _binding: FirstRunRepoSheetBinding? = null
    private val binding get() = _binding!!

    private val repos = mutableListOf(
        RepoItem("Phisher's Repo", "https://raw.githubusercontent.com/Phisher98/StreamPlay/main/repo.json"),
        RepoItem("MegaRepo", "https://raw.githubusercontent.com/megix/phisher-repo/master/repo.json"),
        RepoItem("doGior's Repo", "https://raw.githubusercontent.com/doGior/doGiorHadEnough/main/repo.json"),
        RepoItem("KEKIK Repo", "https://raw.githubusercontent.com/keyiflerolsun/KekikStreamRepo/master/repo.json")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FirstRunRepoSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        binding.repoList.layoutManager = LinearLayoutManager(context)
        binding.repoList.adapter = RepoAdapter(repos)

        binding.btnInstall.setOnClickListener {
            startInstallation()
        }

        binding.btnSkip.setOnClickListener {
            finishFirstRun()
        }
    }

    private fun startInstallation() {
        binding.btnInstall.isEnabled = false
        binding.btnSkip.isEnabled = false
        
        val selectedRepos = repos.filter { it.isChecked }
        if (selectedRepos.isEmpty()) {
            finishFirstRun()
            return
        }

        ioSafe {
            var successCount = 0
            val failedRepos = mutableListOf<RepoItem>()

            selectedRepos.forEachIndexed { index, repoItem ->
                main {
                    binding.tvProgress.text = "Installing ${index + 1} of ${selectedRepos.size}..."
                    repoItem.status = RepoStatus.INSTALLING
                    binding.repoList.adapter?.notifyDataSetChanged()
                }

                try {
                    // Using the existing loadRepository function found in AppContextUtils
                    activity?.loadRepository(repoItem.url)
                    successCount++
                    repoItem.status = RepoStatus.SUCCESS
                } catch (e: Exception) {
                    failedRepos.add(repoItem)
                    repoItem.status = RepoStatus.FAILED
                }

                main {
                    binding.repoList.adapter?.notifyDataSetChanged()
                }
            }

            main {
                if (failedRepos.isEmpty()) {
                    Toast.makeText(context, "All repos installed! Enjoy ZCast ✅", Toast.LENGTH_LONG).show()
                    finishFirstRun()
                } else {
                    showFailureDialog(failedRepos)
                }
            }
        }
    }

    private fun showFailureDialog(failedRepos: List<RepoItem>) {
        val message = "The following repos failed to install:\n" + failedRepos.joinToString("\n") { "- ${it.name}" }
        AlertDialog.Builder(requireContext())
            .setTitle("Some repos failed")
            .setMessage(message)
            .setPositiveButton("Retry Failed") { _, _ ->
                repos.forEach { if (it.status == RepoStatus.FAILED) it.isChecked = true }
                startInstallation()
            }
            .setNegativeButton("Continue Anyway") { _, _ ->
                finishFirstRun()
            }
            .setCancelable(false)
            .show()
    }

    private fun finishFirstRun() {
        val prefs = requireContext().getSharedPreferences("zcast_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("zcast_first_run_done", true).apply()
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class RepoAdapter(private val items: List<RepoItem>) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {
        inner class ViewHolder(val binding: ItemRepoInstallerBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemRepoInstallerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.binding.checkbox.isChecked = item.isChecked
            holder.binding.repoName.text = item.name
            holder.binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }

            holder.binding.statusIndicator.text = when (item.status) {
                RepoStatus.PENDING -> ""
                RepoStatus.INSTALLING -> "Installing..."
                RepoStatus.SUCCESS -> "✅ Done"
                RepoStatus.FAILED -> "❌ Failed"
            }
        }

        override fun getItemCount() = items.size
    }
}
